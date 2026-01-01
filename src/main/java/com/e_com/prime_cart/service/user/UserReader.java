package com.e_com.prime_cart.service.user;

import com.e_com.prime_cart.builder.User;
import com.e_com.prime_cart.builder.dto.user.UserEmail;
import com.e_com.prime_cart.builder.dto.user.UserPublicId;
import com.e_com.prime_cart.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserReader {

  private final UserRepository userRepository;

  public UserReader(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<User> getByEmail(UserEmail userEmail) {
    return userRepository.getOneByEmail(userEmail);
  }

  public Optional<User> getByPublicId(UserPublicId userPublicId) {
    return userRepository.get(userPublicId);
  }
}
