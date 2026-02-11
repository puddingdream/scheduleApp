package com.scheduleapp.schedul.dto;

import com.scheduleapp.comment.dto.GetCommentsResponse;
import com.scheduleapp.schedul.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetOneScheuleResponse {

    private final Long scheduleid;
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final List<GetCommentsResponse> comments;

    public GetOneScheuleResponse(Long scheduleid, String title, String content, String writer, LocalDateTime createdAt, LocalDateTime modifiedAt, List<GetCommentsResponse> comments) {
        this.scheduleid = scheduleid;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.comments = comments;
    }

    public static GetOneScheuleResponse from(Schedule schedule, List<GetCommentsResponse> comments) {
        return new GetOneScheuleResponse(
                schedule.getScheduleId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt(),
                comments
        );
    }

}
