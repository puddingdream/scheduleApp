package com.scheduleapp.comment.entity;

import com.scheduleapp.comment.dto.CreateCommentRequest;
import com.scheduleapp.baseEntity.AuditBaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Comment extends AuditBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 100 ,nullable = false)
    private String comment;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long scheduleId;


    public static Comment from(CreateCommentRequest comment, Long scheduleId ) {
        return new Comment(
                comment.getComment(),
                comment.getWriter(),
                comment.getPassword(),
                scheduleId
        );
    }
    public Comment(String comment, String writer, String password , Long scheduleId) {
        this.comment = comment;
        this.writer = writer;
        this.password = password;
        this.scheduleId = scheduleId;
    }


}
