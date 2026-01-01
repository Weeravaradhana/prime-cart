package com.e_com.prime_cart.security;

import com.e_com.prime_cart.builder.dto.role.Roles;
import com.e_com.prime_cart.builder.dto.role.Username;
import com.e_com.prime_cart.enums.Role;
import com.e_com.prime_cart.exception.Assert;
import com.e_com.prime_cart.exception.NotAuthenticatedUserException;
import com.e_com.prime_cart.exception.UnknownAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class to get authenticated user information
 * Keycloak-compatible version
 */
public final class AuthenticatedUser {

    public static final String PREFERRED_USERNAME = "email";

    private AuthenticatedUser() {
    }

    public static Username username() {
        return optionalUsername().orElseThrow(NotAuthenticatedUserException::new);
    }

    public static Optional<Username> optionalUsername() {
        return authentication().map(AuthenticatedUser::readPrincipal).flatMap(Username::of);
    }

    public static String readPrincipal(Authentication authentication) {
        Assert.notNull("authentication", authentication);

        if (authentication.getPrincipal() instanceof UserDetails details) {
            return details.getUsername();
        }

        if (authentication instanceof JwtAuthenticationToken token) {

            return (String) token.getToken().getClaims().get(PREFERRED_USERNAME);
        }

        if (authentication.getPrincipal() instanceof DefaultOidcUser oidcUser) {
            return (String) oidcUser.getAttributes().get(PREFERRED_USERNAME);
        }

        if (authentication.getPrincipal() instanceof String principal) {
            return principal;
        }

        throw new UnknownAuthenticationException();
    }

    public static Roles roles() {
        return authentication().map(toRoles()).orElse(Roles.EMPTY);
    }

    private static Function<Authentication, Roles> toRoles() {
        return authentication ->
                new Roles(authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(Role::from)
                        .collect(Collectors.toSet()));
    }

    public static Map<String, Object> attributes() {
        Authentication token = authentication().orElseThrow(NotAuthenticatedUserException::new);

        if (token instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes();
        }

        throw new UnknownAuthenticationException();
    }

    private static Optional<Authentication> authentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * Extract roles from Keycloak JWT token
     */
    public static List<String> extractRolesFromToken(Jwt jwt) {
        Object resourceAccessObj = jwt.getClaim("resource_access");
        if (!(resourceAccessObj instanceof Map<?, ?> resourceAccess)) return Collections.emptyList();

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("prime-cart");
        if (clientAccess == null) return Collections.emptyList();

        List<String> roles = (List<String>) clientAccess.get("roles");
        if (roles == null) return Collections.emptyList();

        List<String> list = roles.stream()
                .map(role -> role.replace("-", "_")) // convert Keycloak roles to Spring Security format
                .toList();
        return list;
    }
}
