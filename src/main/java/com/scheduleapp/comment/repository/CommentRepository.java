package com.scheduleapp.comment.repository;

import com.scheduleapp.comment.entity.Comment;
import com.scheduleapp.schedul.dto.ScheduleCommentCount;
import com.scheduleapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findBySchedule_ScheduleId(Long scheduleId); //스케줄에있는 댓글리스트가져오기

    int countBySchedule_ScheduleId(Long scheduleId);

    void deleteBySchedule_ScheduleId(Long scheduleId);

    List<Comment> findAllByUser(User user);

    Optional<Comment> findByCommentIdAndSchedule_ScheduleId(Long commentId, Long scheduleId);

    @Query("""
    select c.schedule.scheduleId as scheduleId,
           count(c.commentId) as count
    from Comment c
    where c.schedule.scheduleId in :ids
    group by c.schedule.scheduleId
""")
    List<ScheduleCommentCount> countByScheduleIds(@Param("ids") List<Long> ids);
}
