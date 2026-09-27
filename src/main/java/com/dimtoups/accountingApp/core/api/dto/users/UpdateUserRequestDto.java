package com.dimtoups.accountingApp.core.api.dto.users;

import com.dimtoups.accountingApp.core.entity.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequestDto(
    @Size(min = User.USERNAME_MIN_LENGTH, max = User.USERNAME_MAX_LENGTH)
    @NotBlank
    String username,

    @Size(min = User.FIRSTNAME_MIN_LENGTH, max = User.FIRSTNAME_MAX_LENGTH)
    @NotBlank
    String firstname,

    @Size(min = User.LASTNAME_MIN_LENGTH, max = User.LASTNAME_MAX_LENGTH)
    @NotBlank
    String lastname) {

}
