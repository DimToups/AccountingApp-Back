package com.dimtoups.accountingApp.core.api.dto.authentification;

import jakarta.validation.constraints.NotBlank;

public record SignupRequestDto(
    @NotBlank
    String username,

    @NotBlank
    String password,

    String firstname,

    String lastname
) {
}
