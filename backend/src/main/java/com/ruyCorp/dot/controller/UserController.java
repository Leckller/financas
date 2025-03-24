package com.ruyCorp.dot.controller;

import com.ruyCorp.dot.controller.dto.User.TokenDto;
import com.ruyCorp.dot.controller.dto.User.UserCreationDto;
import com.ruyCorp.dot.repository.entity.User;
import com.ruyCorp.dot.service.TokenService;
import com.ruyCorp.dot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

  private final UserService userService;
  private final TokenService tokenService;

  @Autowired
  public UserController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<TokenDto> createUser(@Valid @RequestBody UserCreationDto userCreationDto) {

    User user = this.userService.createUser(userCreationDto);

    String token = this.tokenService.generateToken(user.getUsername());

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new TokenDto(token));

  }

}
