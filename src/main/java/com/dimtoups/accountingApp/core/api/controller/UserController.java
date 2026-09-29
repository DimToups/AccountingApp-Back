package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.users.*;
import com.dimtoups.accountingApp.core.api.helper.JwtHelper;
import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.mapper.user.UserRequestsMapper;
import com.dimtoups.accountingApp.core.mapper.user.UserResponseDbMapper;
import com.dimtoups.accountingApp.core.service.UserService;
import jakarta.validation.Valid;
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
  private UserResponseDbMapper userResponseMapper;

  @Autowired
  private UserRequestsMapper userRequestsMapper;

  @GetMapping("/user/{username}")
  public ResponseEntity<UserResponseDto> getUser(
      @PathVariable String username,
      @RequestHeader(name = "Authorization") String bearerToken) {
    // Checking if the client can access the information
    if (!JwtHelper.isClientAuthorized(bearerToken, username)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Finding the user
    Optional<User> optionalUser = userService.findByUsername(username);
    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    UserResponseDto response = userResponseMapper.dbUserToUserResponseDto(optionalUser.get());
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  @DeleteMapping("/user")
  public ResponseEntity<String> deleteUser(
      @RequestBody DeleteUserDto deleteUserDto,
      @RequestHeader(name = "Authorization") String bearerToken) {
    // Checking if the client can access the information
    if (!JwtHelper.isClientAuthorized(bearerToken, deleteUserDto.username())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Deleting the user
    try {
      userService.deleteUser(deleteUserDto.username());
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PutMapping("/user")
  public ResponseEntity<Void> replaceUser(
      @Valid @RequestBody ReplaceUserRequestDto newUser,
      @RequestHeader(name = "Authorization") String bearerToken) {
    // Checking if the client can access the information
    if (!JwtHelper.isClientAuthorized(bearerToken, newUser.username())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Replacing the user
    User dbUser = userRequestsMapper.replaceUserDtoToDbUser(newUser);
    try {
      userService.replaceUser(dbUser);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/user")
  public ResponseEntity<Void> updateUser(
      @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
      @RequestHeader(name = "Authorization") String bearerToken) {
    // Checking if the client can access the information
    if (!JwtHelper.isClientAuthorized(bearerToken, updateUserRequestDto.username())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Update the user
    User updatedDbUser = userRequestsMapper.updtaeUserRequestDtoToDbUser(updateUserRequestDto);
    try {
      userService.updateUser(updatedDbUser);
    } catch (FetchNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
