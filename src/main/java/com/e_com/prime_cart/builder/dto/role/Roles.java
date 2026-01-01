package com.e_com.prime_cart.builder.dto.role;


import com.e_com.prime_cart.enums.Role;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

public record Roles(Set<Role> roles) {

  public static final Roles EMPTY = new Roles(null);

  public Roles(Set<Role> roles) {
      this.roles = Collections.unmodifiableSet(roles);
  }

  public boolean hasRole() {
    return !roles.isEmpty();
  }

  public Stream<Role> stream() {
      return get().stream();
  }

    private Set<Role> get() {
        return roles();
    }
}
