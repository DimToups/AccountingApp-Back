package com.dimtoups.accountingApp.core.mapper.user;

import com.dimtoups.accountingApp.core.api.dto.authentification.SignupRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.DeleteUserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UpdateUserRequestDto;
import com.dimtoups.accountingApp.core.service.dto.users.CreateUserDto;
import com.dimtoups.accountingApp.core.service.dto.users.DeleteUserDto;
import com.dimtoups.accountingApp.core.service.dto.users.UpdateUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserControllerDtoToServiceDtoMapper {

  CreateUserDto createUser(SignupRequestDto signupRequestDto);

  UpdateUserDto updateUser(UpdateUserRequestDto updateUserRequestDto);

  DeleteUserDto deleteUser(DeleteUserRequestDto deleteUserDto);
}
