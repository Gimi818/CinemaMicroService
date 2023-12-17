package com.ticket.common.dto;

import com.ticket.common.enums.AccountType;

public record UserResponseDto(Long id, String firstName,
                              String lastName,
                              String email,
                              AccountType accountType) {
}
