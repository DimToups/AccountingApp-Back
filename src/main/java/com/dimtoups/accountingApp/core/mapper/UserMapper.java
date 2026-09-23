package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.entity.user.User;
import org.mapstruct.*;


@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
  @Mapping(target = "enabled", ignore = true)
  User updateUser(@MappingTarget User userTarget, User userSource);
}
