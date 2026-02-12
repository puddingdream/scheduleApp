package com.scheduleapp.comment.service;

import com.scheduleapp.comment.dto.CreateCommentRequest;
import com.scheduleapp.comment.dto.CreateCommentResponse;
import com.scheduleapp.comment.dto.GetUserAllCommentResponse;
import com.scheduleapp.comment.entity.Comment;
import com.scheduleapp.comment.repository.CommentRepository;
import com.scheduleapp.config.EncoderConfig;
import com.scheduleapp.exception.NullScheduleException;
import com.scheduleapp.exception.UserNullException;
import com.scheduleapp.schedul.entity.Schedule;
import com.scheduleapp.schedul.repository.ScheduleRepository;
import com.scheduleapp.user.dto.SessionUser;
import com.scheduleapp.user.entity.User;
import com.scheduleapp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final EncoderConfig encoderConfig;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(SessionUser sessionUser, Long scheduleId, CreateCommentRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new NullScheduleException("없는 스케줄 입니다."));

        if (commentRepository.countBySchedule_ScheduleId(scheduleId) >= 10) {
            throw new IllegalArgumentException("댓글은 10개까지만 가능합니다.");
        }

        // 로그아웃 상태라면
        if (sessionUser == null) {
            validateCreateGuestCommentRequest(request); // 유저입력검증
            String encodedPassword = encoderConfig.encode(request.password());// 비밀번호 암호화해주기 encode
            Comment comment = commentRepository.save(
                    Comment.fromGuest(request, encodedPassword, schedule)); // 암호된 비번으로 넣기
            return CreateCommentResponse.from(comment);
        }
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new UserNullException("존재하지 않는 유저")
        );

        Comment comment = commentRepository.save(
                Comment.fromUser(request, user, schedule)
        );
        return CreateCommentResponse.from(comment);
    }

    //유저 자기댓글 모아보기
    @Transactional(readOnly = true)
    public List<GetUserAllCommentResponse> getAllComment(SessionUser sessionUser) {
        if (sessionUser == null) {
            throw new UserNullException("로그인상태가 아닙니다.");
        }
        User user = userRepository.findById(sessionUser.id()).orElseThrow(
                () -> new UserNullException("없는 유저입니다.")
        );
        return commentRepository.findAllByUser(user)
                .stream()
                .map(GetUserAllCommentResponse::from)
                .toList();
    }


    // 생성 유저입력검증
    private void validateCreateGuestCommentRequest(CreateCommentRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("요청이 비어있습니다.");
        }
        if (request.comment() == null || request.comment().isBlank()) {
            throw new IllegalArgumentException("댓글은 필수입니다.");
        }
        if (request.comment().length() > 200) {
            throw new IllegalArgumentException("댓글은 200자를 넘을수없습니다.");
        }
        if (request.guestName() == null || request.guestName().isBlank()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
    }

}