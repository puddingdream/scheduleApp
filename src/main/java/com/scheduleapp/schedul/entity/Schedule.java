package com.scheduleapp.schedul.entity;

import com.scheduleapp.baseEntity.AuditBaseEntity;
import com.scheduleapp.schedul.dto.CreateScheduleRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @Column(length = 30, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String content;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String password;


    private Schedule(String title, String content, String writer, String password) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    public void update(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

    public static Schedule from(CreateScheduleRequest schedule) {
        return new Schedule(
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getWriter(),
                schedule.getPassword());
    }

}
