package com.scheduleapp.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private String title;
    private String content;
    private String writer;
    private String password;

}
