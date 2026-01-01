package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;

public record UserEmail(String value) {

  public UserEmail {
    Assert.field("value", value).maxLength(255);
  }
}
