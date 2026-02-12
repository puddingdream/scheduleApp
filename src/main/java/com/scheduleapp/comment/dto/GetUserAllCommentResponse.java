package com.scheduleapp.comment.dto;

import com.scheduleapp.comment.entity.Comment;

import java.time.LocalDateTime;

public record GetUserAllCommentResponse(
        Long commentId,
        String comment,
        String writer,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static GetUserAllCommentResponse from(Comment comment) {
        return new GetUserAllCommentResponse(
                comment.getCommentId(),
                comment.getComment(),
                comment.getUser().getName(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}

