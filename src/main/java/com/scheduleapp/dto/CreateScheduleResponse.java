package com.scheduleapp.dto;

import com.scheduleapp.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {

    private Long scheduleid;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CreateScheduleResponse(Long scheduleid, String title, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.scheduleid = scheduleid;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
