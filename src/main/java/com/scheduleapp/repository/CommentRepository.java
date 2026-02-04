package com.scheduleapp.repository;

import com.scheduleapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countBySchedule_ScheduleId(Long scheduleId);
}
