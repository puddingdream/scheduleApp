package com.scheduleapp.user.dto;

import com.scheduleapp.user.entity.User;

public record SessionUser(
        Long id,
        String email
) {
    public static SessionUser from(User user) {
        return new SessionUser(
                user.getUserId(),
                user.getEmail()
        );
    }
}
