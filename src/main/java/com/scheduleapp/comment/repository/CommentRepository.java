package com.scheduleapp.comment.repository;

import com.scheduleapp.comment.entity.Comment;
import com.scheduleapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySchedule_ScheduleId(Long scheduleId); //스케줄에있는 댓글리스트가져오기

    int countBySchedule_ScheduleId(Long scheduleId);

    void deleteBySchedule_ScheduleId(Long scheduleId);

    List<Comment> findAllByUser(User user);

    Optional<Comment> findByCommentIdAndSchedule_ScheduleId(Long commentId, Long scheduleId);
}
