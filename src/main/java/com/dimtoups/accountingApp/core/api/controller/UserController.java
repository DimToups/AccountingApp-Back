package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.users.UserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UserResponseDto;
import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.mapper.UserRequestDbMapper;
import com.dimtoups.accountingApp.core.mapper.UserResponseDbMapper;
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

  @Autowired
  private UserResponseDbMapper userResponseDbMapper;

  @Autowired
  private UserRequestDbMapper userRequestDbMapper;

  @GetMapping("/user/{username}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable String username) {
    Optional<User> optionalUser = userService.findByUsername(username);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(404).build();
    }

    UserResponseDto response = userResponseDbMapper.dbUserToUserResponseDto(optionalUser.get());
    return ResponseEntity.status(200).body(response);
  }

  @DeleteMapping("/user")
  public ResponseEntity<String> deleteUser(@RequestBody String username) {
    try {
      userService.deleteUser(username);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(200).build();
  }

  @PutMapping("/user")
  public ResponseEntity<String> replaceUser(@RequestBody UserRequestDto newUser) {
    User dbUser = userRequestDbMapper.requestDtoToDbUser(newUser);

    try {
      userService.replaceUser(dbUser);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PatchMapping("/user")
  public ResponseEntity<User> updateUser(@RequestBody UserRequestDto updatedUser) {
    User updatedDbUser = userRequestDbMapper.requestDtoToDbUser(updatedUser);

    try {
      userService.updateUser(updatedDbUser);
    } catch (FetchNotFoundException exception) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }
}
