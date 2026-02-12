package com.scheduleapp.comment.entity;

import com.scheduleapp.baseEntity.SoftDeleteEntity;
import com.scheduleapp.comment.dto.CreateCommentRequest;
import com.scheduleapp.schedul.entity.Schedule;
import com.scheduleapp.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Comment extends SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 100 ,nullable = false)
    private String comment;

    private String guestName;
    private String guestPasswordHash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_Id", nullable = false)
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;


    public static Comment fromGuest(
            CreateCommentRequest comment,
            String guestPasswordHash,
            Schedule schedule ) {
        return new Comment(
                comment.comment(),
                comment.guestName(),
                guestPasswordHash,
                schedule
        );
    }
    public static Comment fromUser(
            CreateCommentRequest comment,
            User user,
            Schedule schedule ) {
        return new Comment(
                comment.comment(),
                user,
                schedule
        );
    }

    public Comment(String comment, User user, Schedule schedule) {
        this.comment = comment;
        this.schedule = schedule;
        this.user = user;
        this.guestName = null;
        this.guestPasswordHash = null;
    }

    public Comment(String comment, String guestName, String guestPasswordHash , Schedule schedule) {
        this.comment = comment;
        this.guestName = guestName;
        this.guestPasswordHash = guestPasswordHash;
        this.schedule = schedule;
    }
}
