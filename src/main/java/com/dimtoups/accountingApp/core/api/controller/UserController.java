package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.users.ReplaceUserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UpdateUserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UserResponseDto;
import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.mapper.user.UserRequestsMapper;
import com.dimtoups.accountingApp.core.mapper.user.UserResponseDbMapper;
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
  private UserResponseDbMapper userResponseMapper;

  @Autowired
  private UserRequestsMapper userRequestsMapper;

  @GetMapping("/user")
  public ResponseEntity<UserResponseDto> getUser(@RequestBody String username) {
    Optional<User> optionalUser = userService.findByUsername(username);

    if (optionalUser.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    UserResponseDto response = userResponseMapper.dbUserToUserResponseDto(optionalUser.get());
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
  }

  @DeleteMapping("/user")
  public ResponseEntity<String> deleteUser(@RequestBody String username) {
    try {
      userService.deleteUser(username);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PutMapping("/user")
  public ResponseEntity<Void> replaceUser(@RequestBody ReplaceUserRequestDto newUser) {
    User dbUser = userRequestsMapper.replaceUserDtoToDbUser(newUser);

    try {
      userService.replaceUser(dbUser);
    } catch (FetchNotFoundException e) {
      return ResponseEntity.status(404).build();
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/user")
  public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequestDto updateUserRequestDto) {
    User updatedDbUser = userRequestsMapper.updtaeUserRequestDtoToDbUser(updateUserRequestDto);

    try {
      userService.updateUser(updatedDbUser);
    } catch (FetchNotFoundException exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
