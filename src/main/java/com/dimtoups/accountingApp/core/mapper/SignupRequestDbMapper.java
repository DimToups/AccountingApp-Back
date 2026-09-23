package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.api.dto.authentification.SignupRequestDto;
import com.dimtoups.accountingApp.core.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignupRequestDbMapper {
  User signupRequestToUser(SignupRequestDto signupRequestDto);
}
