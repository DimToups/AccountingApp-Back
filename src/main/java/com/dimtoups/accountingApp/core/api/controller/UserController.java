package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.users.UserConverter;
import com.dimtoups.accountingApp.core.api.dto.users.UserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UserResponseDto;
import com.dimtoups.accountingApp.core.entity.User;
import com.dimtoups.accountingApp.core.service.UserService;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  private final UserConverter userConverter = new UserConverter();

  @GetMapping("/user/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
    Optional<User> optionalUser = userService.findById(id);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    UserResponseDto response = userConverter.convertToUserResponseDto(optionalUser.get());
    return ResponseEntity.status(200).body(response);
  }

  @PostMapping("/user")
  public ResponseEntity<String> createUser(@RequestBody UserRequestDto userDto) {
    User user = userConverter.convertToUser(userDto);

    userService.createUser(user);

    return ResponseEntity.status(201).build();
  }

  @DeleteMapping("/user/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    try {
      userService.deleteUser(id);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(200).build();
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<String> replaceUser(@PathVariable Long id, @RequestBody UserRequestDto newUser) {
    User dbUser = userConverter.convertToUser(newUser, id);

    try {
      userService.replaceUser(dbUser);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PatchMapping("/user/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserRequestDto updatedUser) {
    User updatedDbUser = userConverter.convertToUser(updatedUser, id);

    try {
      userService.updateUser(id, updatedDbUser);
    } catch (FetchNotFoundException exception) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
