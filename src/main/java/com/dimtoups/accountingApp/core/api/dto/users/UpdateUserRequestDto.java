package com.dimtoups.accountingApp.core.api.dto.users;

public record UpdateUserRequestDto(
    String username,
    String firstname,
    String lastname) {

}
