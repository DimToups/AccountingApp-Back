package com.dimtoups.accountingApp.core.api.dto.authentification;

public record LoginRequestDto(
    String username,
    String password) {
}
