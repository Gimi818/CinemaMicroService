package com.user.user.dto;

import com.user.user.AccountType;

public record UserResponseDto(String firstName,
                              String lastName,
                              String email,
                              AccountType accountType) {
}
