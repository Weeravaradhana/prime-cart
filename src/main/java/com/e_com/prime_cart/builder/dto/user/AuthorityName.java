package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;

public record AuthorityName(String name) {
    public AuthorityName {
        Assert.field("name",name).notNull();
    }
}
