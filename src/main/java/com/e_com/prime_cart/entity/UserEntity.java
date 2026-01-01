package com.e_com.prime_cart.entity;

import com.e_com.prime_cart.builder.User;
import com.e_com.prime_cart.builder.UserBuilder;
import com.e_com.prime_cart.builder.dto.user.*;
import com.e_com.prime_cart.common.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "ecommerce_user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "email")
  private String email;

  @Column(name = "image_url")
  private String imageURL;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "address_street")
  private String addressStreet;

  @Column(name = "address_city")
  private String addressCity;

  @Column(name = "address_zip_code")
  private String addressZipCode;

  @Column(name = "address_country")
  private String addressCountry;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @ManyToMany()
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  public void updateFromUser(User user) {
    this.email = user.getEmail().value();
    this.lastName = user.getLastname().value();
    this.firstName = user.getFirstname().value();
    this.imageURL = user.getImageUrl().value();
    this.lastSeen = user.getLastSeen();
  }

  public static UserEntity from(User user) {

    UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();

    if (user.getImageUrl() != null) {
      userEntityBuilder.imageURL(user.getImageUrl().value());
    }

    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    if (user.getUserAddress() != null) {
      userEntityBuilder.addressCity(user.getUserAddress().city());
      userEntityBuilder.addressCountry(user.getUserAddress().country());
      userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
      userEntityBuilder.addressStreet(user.getUserAddress().street());
    }

      return userEntityBuilder
              .authorities(AuthorityEntity.from(user.getAuthorities()))
              .email(user.getEmail().value())
              .firstName(user.getFirstname().value())
              .lastName(user.getLastname().value())
              .lastSeen(user.getLastSeen())
              .id(user.getDbId())
              .build();
  }

  public static User toDomain(UserEntity userEntity) {
    UserBuilder userBuilder = UserBuilder.user();
    if(userEntity.getImageURL() != null) {
      userBuilder.imageUrl(new UserImageUrl(userEntity.getImageURL()));
    }

    if(userEntity.getAddressStreet() != null) {
      userBuilder.userAddress(
        UserAddressBuilder.userAddress()
          .city(userEntity.getAddressCity())
          .country(userEntity.getAddressCountry())
          .zipCode(userEntity.getAddressZipCode())
          .street(userEntity.getAddressStreet())
          .build());
    }

    User build = userBuilder
            .email(new UserEmail(userEntity.getEmail()))
            .lastname(new UserLastname(userEntity.getLastName()))
            .firstname(new UserFirstname(userEntity.getFirstName()))
            .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
            .userPublicId(new UserPublicId(userEntity.getPublicId()))
            .lastModifiedDate(userEntity.getLastModifiedDate())
            .createdDate(userEntity.getCreateDate())
            .dbId(userEntity.getId())
            .build();
    from(build);
    return build;
  }


  public static Set<User> toDomain(List<UserEntity> users) {
    return users.stream().map(UserEntity::toDomain).collect(Collectors.toSet());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
