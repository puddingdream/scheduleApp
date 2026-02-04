package com.scheduleapp.repository;

import com.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countBySchedule_ScheduleId(Long scheduleId);

    List<Comment> findBySchedule_ScheduleId(Long scheduleId);
}
