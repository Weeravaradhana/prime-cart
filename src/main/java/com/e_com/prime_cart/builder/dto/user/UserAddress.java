package com.e_com.prime_cart.builder.dto.user;

import com.e_com.prime_cart.exception.Assert;
import org.jilt.Builder;

@Builder
public record UserAddress(String street, String city, String zipCode, String country) {
    public UserAddress {
        Assert.field("street", street).notNull();
        Assert.field("city", city).notNull();
        Assert.field("zipCode", zipCode).notNull();
        Assert.field("country", country).notNull();
    }
}
