package com.scheduleapp.comment.service;

import com.scheduleapp.comment.dto.CreateCommentRequest;
import com.scheduleapp.comment.dto.CreateCommentResponse;
import com.scheduleapp.comment.entity.Comment;
import com.scheduleapp.comment.repository.CommentRepository;
import com.scheduleapp.exception.NullScheduleException;
import com.scheduleapp.schedul.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse save(Long scheduleId, CreateCommentRequest request) {
        validateCreateCommentRequest(request); // 유저입력검증

        if (!scheduleRepository.existsById(scheduleId)) {
            throw new NullScheduleException("없는 스케줄 입니다.");
        }
        if (commentRepository.countByScheduleId(scheduleId) >= 10) {
            throw new IllegalArgumentException("댓글은 10개까지만 가능합니다.");
        }
        Comment comment = commentRepository.save(
                Comment.from(request, scheduleId)
        );
        return CreateCommentResponse.from(comment);
    }

    // 생성 유저입력검증
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
