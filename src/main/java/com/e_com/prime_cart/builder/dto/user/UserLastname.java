package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;

public record UserLastname (String value){
    public UserLastname{
        Assert.field("value",value).maxLength(255);
    }
}
