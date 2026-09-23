package com.dimtoups.accountingApp.core.api.dto.authentification;

import org.springframework.security.web.csrf.CsrfToken;

public record LoginResponseDto(
    String token,
    CsrfToken csrfToken
) {
}
