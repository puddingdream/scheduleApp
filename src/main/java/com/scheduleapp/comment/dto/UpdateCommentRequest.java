package com.scheduleapp.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateCommentRequest(
        @NotBlank(message = "수정할 댓글을 입력하세요.")
        @Size(min = 3, max = 200, message = "200자를 넘길수없습니다.")
        String comment,
        @Size(min = 4, max = 20, message = "비밀번호는 4자리이상 20자리 이하여야 합니다.")
        String password) {
}
