package com.e_com.prime_cart.repo;

import com.e_com.prime_cart.builder.User;
import com.e_com.prime_cart.builder.dto.user.UserAddressToUpdate;
import com.e_com.prime_cart.builder.dto.user.UserEmail;
import com.e_com.prime_cart.builder.dto.user.UserPublicId;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> get(UserPublicId userPublicId);
    Optional<User> getOneByEmail(UserEmail userEmail);
    void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress);
}
