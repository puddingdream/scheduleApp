package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

public record LoginResponse(
        Long id,
        String email
) {
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getUserId(),
                user.getEmail()
        );
    }
}
