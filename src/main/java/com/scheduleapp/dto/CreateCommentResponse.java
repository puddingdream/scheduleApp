package com.scheduleapp.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final  Long commentId;
    private final String comment;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public CreateCommentResponse(Long commentId, String comment, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
