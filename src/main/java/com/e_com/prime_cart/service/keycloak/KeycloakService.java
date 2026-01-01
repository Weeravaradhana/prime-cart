package com.e_com.prime_cart.service.keycloak;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KeycloakService {
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new IllegalStateException("No Keycloak token found");
        }
        return jwt.getClaims();
    }
}
