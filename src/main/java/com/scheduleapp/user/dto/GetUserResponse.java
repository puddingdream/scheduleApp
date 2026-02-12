package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

import java.time.LocalDateTime;

public record GetUserResponse(
        Long userId,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static GetUserResponse from(User user) {
       return new GetUserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
               user.getCreatedAt(),
               user.getModifiedAt());
    }
}
