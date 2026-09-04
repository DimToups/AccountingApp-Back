package com.dimtoups.accountingApp.core.api.dto.users;

import com.dimtoups.accountingApp.core.entity.User;
import com.dimtoups.accountingApp.core.entity.builder.UserBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserConverter {
  public UserResponseDto convertToUserResponseDto(User user) {
    return new UserResponseDto(user.getFirstname(), user.getLastname());
  }

  public Collection<UserResponseDto> convertToUserResponseDtoCollection(Collection<User> userList) {
    List<UserResponseDto> userDtoList = new ArrayList<>(userList.size());
    userList.forEach(user -> userDtoList.add(this.convertToUserResponseDto(user)));
    return userDtoList;
  }

  public User convertToUser(UserRequestDto userDto) {
    return new UserBuilder()
        .setUsername(userDto.username())
        .setPassword(userDto.password())
        .setFirstname(userDto.firstname())
        .setLastname(userDto.lastname())
        .build();
  }

  public User convertToUser(UserRequestDto userDto, String username) {
    return new UserBuilder()
        .setUsername(username)
        .setFirstname(userDto.firstname())
        .setLastname(userDto.lastname())
        .build();
  }
}
