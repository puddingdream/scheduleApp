package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

import java.time.LocalDateTime;

public record CreateUserResponse(
        Long id,
        String name,
        String email,
        String password,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static CreateUserResponse from(User user) {
        return new CreateUserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

}
