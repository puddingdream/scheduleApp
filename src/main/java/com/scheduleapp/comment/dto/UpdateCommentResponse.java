package com.scheduleapp.comment.dto;

import com.scheduleapp.comment.entity.Comment;

import java.time.LocalDateTime;

public record UpdateCommentResponse(
        Long commentId,
        String comment,
        String writer,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UpdateCommentResponse from(Comment comment) {
        String writer = comment.getUser() != null
                ? comment.getUser().getName()
                : comment.getGuestName();

        return new UpdateCommentResponse(
                comment.getCommentId(),
                comment.getComment(),
                writer,
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}

