package com.scheduleapp.comment.repository;

import com.scheduleapp.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByScheduleId(Long scheduleId); //스케줄에있는 댓글리스트가져오기

    int countByScheduleId(Long scheduleId);

    void deleteByScheduleId(Long scheduleId);
}
