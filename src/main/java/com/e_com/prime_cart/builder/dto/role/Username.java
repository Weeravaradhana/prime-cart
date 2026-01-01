package com.e_com.prime_cart.builder.dto.role;

import io.micrometer.common.util.StringUtils;

import java.util.Optional;

public record Username(String username) {
  public String get(){return username();
  }

  public static Optional<Username> of(String username){
      return Optional.ofNullable(username).filter(StringUtils::isNotBlank).map(Username::new);
  }
}
