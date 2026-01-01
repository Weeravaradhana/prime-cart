package com.e_com.prime_cart.builder;

import com.e_com.prime_cart.builder.dto.user.AuthorityName;
import com.e_com.prime_cart.exception.Assert;
import lombok.Getter;
import org.jilt.Builder;

@Getter
@Builder
public class Authority {
    private AuthorityName name;

    public Authority(AuthorityName authorityName) {
        Assert.notNull("name", authorityName);
        this.name = authorityName;
    }

}
