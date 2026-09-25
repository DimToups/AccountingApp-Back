package com.dimtoups.accountingApp.core.mapper.user;

import com.dimtoups.accountingApp.core.api.dto.users.ReplaceUserRequestDto;
import com.dimtoups.accountingApp.core.api.dto.users.UpdateUserRequestDto;
import com.dimtoups.accountingApp.core.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestsMapper {

  User updtaeUserRequestDtoToDbUser(UpdateUserRequestDto userRequestDto);

  User replaceUserDtoToDbUser(ReplaceUserRequestDto newUser);
}
