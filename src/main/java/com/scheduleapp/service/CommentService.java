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
        validateCreateCommentRequest(request);
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

    private void validateCreateCommentRequest(CreateCommentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 비어있습니다.");
        }
        if (request.getComment() == null || request.getComment().isBlank()) {
            throw new IllegalArgumentException("댓글은 필수입니다.");
        }
        if (request.getComment().length() > 100) {
            throw new IllegalArgumentException("댓글은 100자를 넘을수없습니다.");
        }
        if (request.getWriter() == null || request.getWriter().isBlank()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
    }
}
