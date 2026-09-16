package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.api.dto.users.UserRequestDto;
import com.dimtoups.accountingApp.core.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestDbMapper {

  User requestDtoToDbUser(UserRequestDto userRequestDto);

  User requestDtoToDbUser(UserRequestDto userRequestDto, long id);
}
