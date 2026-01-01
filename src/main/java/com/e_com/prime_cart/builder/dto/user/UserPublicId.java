package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;

import java.util.UUID;

public record UserPublicId(UUID value) {

  public UserPublicId {
    Assert.notNull("value", value);
  }
}
