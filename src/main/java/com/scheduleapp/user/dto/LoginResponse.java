package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

import java.time.LocalDateTime;

public record LoginResponse(
        Long id,
        String email,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static LoginResponse from(User user) {
        return new LoginResponse(
                user.getUserId(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
