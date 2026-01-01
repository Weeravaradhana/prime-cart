package com.e_com.prime_cart.builder;

import com.e_com.prime_cart.builder.dto.user.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private UserLastname lastname;

    private UserFirstname firstname;

    private UserEmail email;

    private UserPublicId userPublicId;

    private UserImageUrl imageUrl;

    private Instant lastModifiedDate;

    private Instant createdDate;

    private Set<Authority> authorities;

    private Long dbId;

    private UserAddress userAddress;

    private Instant lastSeen;

    public void updateFromUser(User user) {
        this.email = user.email;
        this.imageUrl = user.imageUrl;
        this.firstname = user.firstname;
        this.lastname = user.lastname;
    }

    public void initFieldForSignup() {
        this.userPublicId = new UserPublicId(UUID.randomUUID());
    }

    public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
        UserBuilder userBuilder = UserBuilder.user();

        if(attributes.containsKey("email")) {
            userBuilder.email(new UserEmail(attributes.get("email").toString()));
        }

        if(attributes.containsKey("family_name")) {
            userBuilder.lastname(new UserLastname(attributes.get("family_name").toString()));
        }

        if(attributes.containsKey("given_name")) {
            userBuilder.firstname(new UserFirstname(attributes.get("given_name").toString()));
        }

        if(attributes.containsKey("picture")) {
            userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
        }

        if(attributes.containsKey("last_signed_in")) {
            userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
        }

        Set<Authority> authorities = rolesFromAccessToken
                .stream()
                .map(authority -> AuthorityBuilder.authority().name(new AuthorityName(authority)).build())
                .collect(Collectors.toSet());

        userBuilder.authorities(authorities);

        return userBuilder.build();

    }
}
