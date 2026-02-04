package com.scheduleapp.dto;

import com.scheduleapp.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentsResponse {

    private final Long commentId;
    private final String comment;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetCommentsResponse(Long commentId, String comment, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentId = commentId;
        this.comment = comment;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static  GetCommentsResponse from(Comment comment) {
        return new GetCommentsResponse(
                comment.getCommentId(),
                comment.getComment(),
                comment.getWriter(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }
}
