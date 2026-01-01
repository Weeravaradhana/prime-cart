package com.e_com.prime_cart.entity;

import com.e_com.prime_cart.builder.Authority;
import com.e_com.prime_cart.builder.AuthorityBuilder;
import com.e_com.prime_cart.builder.dto.user.AuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "authority")
@Builder
public class AuthorityEntity implements Serializable {

  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;

  public AuthorityEntity() {
  }

  public AuthorityEntity(String name) {
    this.name = name;
  }

  public static Set<AuthorityEntity> from(Set<Authority> authorities) {

    Set<AuthorityEntity> collect = authorities.stream()
            .map(authority -> AuthorityEntityBuilder.authorityEntity()
                    .name(authority.getName().name()).build()).collect(Collectors.toSet());
    System.out.println("from: " + collect);
    return collect;
  }

  public static Set<Authority> toDomain(Set<AuthorityEntity> authorityEntities) {
    Set<Authority> collect = authorityEntities.stream()
            .map(authorityEntity -> AuthorityBuilder.authority()
                    .name(new AuthorityName(authorityEntity.name))
                    .build())
            .collect(Collectors.toSet());
    return collect;
  }

  public @NotNull @Size(max = 50) String getName() {
    return name;
  }

  public void setName(@NotNull @Size(max = 50) String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AuthorityEntity that)) return false;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
