package com.scheduleapp.comment.dto;

import jakarta.validation.constraints.Size;

public record DeleteCommentRequest(
        @Size(min = 4, max = 20, message = "비밀번호는 4자리이상 20자리 이하여야 합니다.")
        String password) {
}
