package com.dimtoups.accountingApp.core.mapper;

import com.dimtoups.accountingApp.core.entity.User;
import org.mapstruct.*;


@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
  User updateUser(@MappingTarget User userTarget, User userSource);
}
