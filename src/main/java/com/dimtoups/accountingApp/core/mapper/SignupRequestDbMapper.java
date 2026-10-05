package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.service.dto.users.CreateUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignupRequestDbMapper {
  User signupRequestToUser(CreateUserDto createUserDto);
}
