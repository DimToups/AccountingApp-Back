package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.api.dto.users.UserResponseDto;
import com.dimtoups.accountingApp.core.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseDbMapper {

  UserResponseDto dbUserToUserResponseDto(User user);
}
