package com.scheduleapp.entity;

import com.scheduleapp.dto.CreateCommentRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(length = 100 ,nullable = false)
    private String comment;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY) // 1개에 여러개 붙을예정
    @JoinColumn(name = "scheduleId", nullable = false) // FK 연결해주기
    private Schedule schedule;

    public static Comment from(CreateCommentRequest comment,Schedule schedule ) {
        return new Comment(
                comment.getComment(),
                comment.getWriter(),
                comment.getPassword(),
                schedule
        );
    }
    public Comment(String comment, String writer, String password , Schedule schedule) {
        this.comment = comment;
        this.writer = writer;
        this.password = password;
        this.schedule = schedule;
    }


}
