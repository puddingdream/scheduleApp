package com.scheduleapp.comment.dto;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private String comment;
    private String writer;
    private String password;

}
