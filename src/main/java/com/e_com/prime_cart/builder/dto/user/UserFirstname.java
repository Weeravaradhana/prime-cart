package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;

public record UserFirstname(String value) {
    public  UserFirstname {
        Assert.field("value",value).maxLength(255);
    }
}
