package com.dimtoups.accountingApp.core.mapper.user;

import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.service.dto.users.UpdateUserDto;
import org.mapstruct.*;


@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DbUserMapper {
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "password", ignore = true)
  User updateUser(@MappingTarget User userTarget, UpdateUserDto userSource);
}
