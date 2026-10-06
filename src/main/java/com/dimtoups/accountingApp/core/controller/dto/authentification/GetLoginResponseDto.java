package com.dimtoups.accountingApp.core.controller.dto.authentification;

import org.springframework.security.web.csrf.CsrfToken;

public record GetLoginResponseDto(
    CsrfToken csrfToken
) {
}
