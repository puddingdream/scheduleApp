package com.scheduleapp.user.dto;

public record LoginResponse(
        Long id,
        String name,
        String email
) {
}
