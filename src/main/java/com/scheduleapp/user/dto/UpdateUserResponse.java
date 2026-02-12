package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

import java.time.LocalDateTime;

public record UpdateUserResponse(
        Long id,
        String name,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UpdateUserResponse from(User user) {
        return new UpdateUserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
