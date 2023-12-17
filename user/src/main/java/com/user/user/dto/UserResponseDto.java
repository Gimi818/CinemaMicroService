package com.user.user.dto;

import com.user.user.AccountType;

public record UserResponseDto(
        Long id, String firstName,
        String lastName,
        String email,
        AccountType accountType) {
}
