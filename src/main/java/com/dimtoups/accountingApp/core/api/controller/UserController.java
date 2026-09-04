package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.LoginRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UserConverter;
import com.dimtoups.accountingApp.core.api.dto.users.UserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UserResponseDto;
import com.dimtoups.accountingApp.core.entity.User;
import com.dimtoups.accountingApp.core.service.UserService;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  private final UserConverter userConverter = new UserConverter();

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
    Optional<User> optionalUser = userService.findById(id);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    UserResponseDto response = userConverter.convertToUserResponseDto(optionalUser.get());
    return ResponseEntity.status(200).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<String> logIn(@RequestBody LoginRequestDto loginRequestDto) {

    return ResponseEntity.status(200).build();
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signUp(@RequestBody UserRequestDto userDto, Authentication authentication) {
    // Checking if the username is available
    Optional<User> potentialExistingUser = userService.findByUsername(userDto.username());
    if (potentialExistingUser.isPresent()) {
      // A user already has this username
      return ResponseEntity.status(422).build();
    }

    // Creating the user and retrieving its db value
    User createdUser = userService.createUser(userConverter.convertToUser(userDto));

    // Logging out the current user
    if (authentication != null && authentication.isAuthenticated()) {
      System.out.println("Log out");
      System.out.println("Coming soon");
    }
    // Logging in the newly created user


    return ResponseEntity.status(201).build();
  }

  @DeleteMapping("/user/{username}")
  public ResponseEntity<String> deleteUser(@PathVariable String username) {
    try {
      userService.deleteUser(username);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(200).build();
  }

  @PutMapping("/user/{username}")
  public ResponseEntity<String> replaceUser(@PathVariable String username, @RequestBody UserRequestDto newUser) {
    User dbUser = userConverter.convertToUser(newUser, username);

    try {
      userService.replaceUser(dbUser);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PatchMapping("/user/{username}")
  public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody UserRequestDto updatedUser) {
    User updatedDbUser = userConverter.convertToUser(updatedUser, username);

    try {
      userService.updateUser(username, updatedDbUser);
    } catch (FetchNotFoundException exception) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
