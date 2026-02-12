package com.scheduleapp.comment.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank(message = "댓글은 필수입니다.")
        @Size(min = 3, max = 200, message = "200자를 넘길수없습니다.")
        String comment,
        String guestName,
        String password ) {
}
