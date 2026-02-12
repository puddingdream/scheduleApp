package com.scheduleapp.user.dto;

import jakarta.validation.constraints.NotNull;

public record DeleteUserRequest(
        @NotNull(message = "비밀번호는 필수입니다.")
        String password) {
}
