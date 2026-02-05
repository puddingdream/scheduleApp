package com.scheduleapp.repository;

import com.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countBySchedule_ScheduleId(Long scheduleId); // 스케줄에있는 댓글갯수

    List<Comment> findBySchedule_ScheduleId(Long scheduleId); //스케줄에있는 댓글리스트가져오기
}
