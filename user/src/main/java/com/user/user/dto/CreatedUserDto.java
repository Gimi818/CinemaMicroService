package com.user.user.dto;

public record CreatedUserDto(
        Long id,
        String firstName,
        String lastName,
        String email) {
}
