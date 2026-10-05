package com.dimtoups.accountingApp.core.controller.dto.users;

import com.dimtoups.accountingApp.core.entity.user.User;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record DeleteUserRequestDto(
    @Length(min = User.USERNAME_MIN_LENGTH, max = User.USERNAME_MAX_LENGTH)
    @NotBlank
    String username
) {
}
