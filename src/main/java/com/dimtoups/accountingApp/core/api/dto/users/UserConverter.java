package com.dimtoups.accountingApp.core.api.dto.users;

import com.dimtoups.accountingApp.core.entity.User;

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
    return new User(userDto.firstname(), userDto.lastname());
  }

  public User convertToUser(UserRequestDto userDto, Long id) {
    return new User(id, userDto.firstname(), userDto.lastname());
  }
}
