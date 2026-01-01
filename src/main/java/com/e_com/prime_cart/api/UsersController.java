package com.e_com.prime_cart.api;

import com.e_com.prime_cart.builder.User;
import com.e_com.prime_cart.builder.dto.user.RestUser;
import com.e_com.prime_cart.service.user.UsersApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  private final UsersApplicationService usersApplicationService;

  public UsersController(UsersApplicationService usersApplicationService) {
    System.out.println("Inside UsersResource constructor");
    this.usersApplicationService = usersApplicationService;
  }

  @GetMapping("/authenticated")
  public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwtToken,
                                                       @RequestParam boolean forceResync)
  {
    User authenticatedUser = usersApplicationService.getAuthenticatedUserWithSync(jwtToken, forceResync);
    RestUser restUser = RestUser.from(authenticatedUser);
    return ResponseEntity.ok(restUser);
  }

}
