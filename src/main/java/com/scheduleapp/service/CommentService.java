package com.scheduleapp.service;

import com.scheduleapp.dto.CreateCommentRequest;
import com.scheduleapp.dto.CreateCommentResponse;
import com.scheduleapp.entity.Comment;
import com.scheduleapp.entity.Schedule;
import com.scheduleapp.repository.CommentRepository;
import com.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId,CreateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 스케줄 입니다."));
        if (commentRepository.countBySchedule_ScheduleId(scheduleId)>=10) {
            throw new IllegalArgumentException("댓글은 10개까지만 가능");
        }
          Comment comment = commentRepository.save(new Comment(
                  request.getComment(),
                  request.getWriter(),
                  request.getPassword(),
                  schedule));
          return new CreateCommentResponse(
                  comment.getCommentId(),
                  comment.getComment(),
                  comment.getWriter(),
                  comment.getCreatedAt(),
                  comment.getModifiedAt()
          );
    }
}
