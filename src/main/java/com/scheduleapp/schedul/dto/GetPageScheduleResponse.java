package com.scheduleapp.schedul.dto;

import com.scheduleapp.schedul.entity.Schedule;

import java.time.LocalDateTime;

public record GetPageScheduleResponse(
        Long id,
        String title,
        String content,
        String writer,
        int commentCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {



    public static GetPageScheduleResponse of(Schedule schedule, int commentCount) {
        return new GetPageScheduleResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
}
