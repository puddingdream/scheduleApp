package com.scheduleapp.comment.dto;


public record CreateCommentRequest(
        String comment,
        String guestName,
        String password ) {
}
