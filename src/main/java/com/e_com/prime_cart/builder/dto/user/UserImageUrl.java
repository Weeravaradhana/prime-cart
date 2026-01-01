package com.e_com.prime_cart.builder.dto.user;


import com.e_com.prime_cart.exception.Assert;

public record UserImageUrl(String value) {

  public UserImageUrl {
    Assert.field("value", value).maxLength(1000);
  }
}
