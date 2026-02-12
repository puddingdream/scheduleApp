package com.scheduleapp.comment.service;

import com.scheduleapp.comment.dto.*;
import com.scheduleapp.comment.entity.Comment;
import com.scheduleapp.comment.repository.CommentRepository;
import com.scheduleapp.config.EncoderConfig;
import com.scheduleapp.exception.*;
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
                () -> new UserNullException("존재하지 않는 유저입니다.")  // 필요한지 모르겠음
        );
        return commentRepository.findAllByUser(user)
                .stream()
                .map(GetUserAllCommentResponse::from)
                .toList();
    }

    /*
    *             if (!encoderConfig.matches(request.password(), comment.getGuestPasswordHash()) {
                throw new PasswordMissException("비밀번호가 일치하지 않습니다.");
    *    예를 들어:
                유저가 로그인한 뒤 계정 삭제됨
                DB 롤백/초기화로 해당 유저가 없어짐
                세션 정보가 오래 살아남아 불일치 발생
    *  위 내용의 이유로 일단 예외처리
    * */

    @Transactional
    public UpdateCommentResponse updateComment(
            SessionUser sessionUser, Long scheduleId, Long commentId, UpdateCommentRequest request) {
        Comment comment = commentRepository.findByCommentIdAndSchedule_ScheduleId(commentId, scheduleId).orElseThrow(
                () -> new NullCommentException("존재하지 않는 댓글 입니다."));

        if (sessionUser == null) {
            if (comment.getUser() != null) {
                throw new UnauthorizedCommentException("댓글은 작성자만 수정할 수 있습니다.");
            }
            if (request.password() == null || request.password().isBlank()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다.");
            }
            if (!encoderConfig.matches(request.password(), comment.getGuestPasswordHash())) {
                throw new PasswordMissException("비밀번호가 일치하지 않습니다.");
            }
            comment.update(request.comment());
            return UpdateCommentResponse.from(comment);
        }
        if (comment.getUser() == null) {
            throw new UnauthorizedCommentException("댓글은 작성자만 수정할 수 있습니다.");
        }
        if (comment.getUser().getUserId().equals(sessionUser.id())) {
            comment.update(request.comment());
            return UpdateCommentResponse.from(comment);
        }
        throw new UnauthorizedCommentException("권한없는 요청입니다.");
    }

    @Transactional
    public void deleteComment(
            Long scheduleId, Long commentId, DeleteCommentRequest request, SessionUser sessionUser) {
        Comment comment = commentRepository.findByCommentIdAndSchedule_ScheduleId(commentId, scheduleId).orElseThrow(
                () -> new NullCommentException("존재하지 않는 댓글 입니다."));

        if (sessionUser != null) {
            if (comment.getUser() != null && comment.getUser().getUserId().equals(sessionUser.id())) {
                comment.delete();
                return;
            }
            throw new UnauthorizedCommentException("댓글은 작성자만 지울수있습니다.");
        }
        if (comment.getUser() != null) {
            throw new UnauthorizedCommentException("댓글은 작성자만 지울수있습니다.");
        }
        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }
        if (!encoderConfig.matches(request.password(), comment.getGuestPasswordHash())) {
            throw new PasswordMissException("비밀번호가 일치하지 않습니다.");
        }
        comment.delete();
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
        if (request.password().length() < 4 || request.password().length() > 20) {
            throw new IllegalArgumentException("비밀번호는 4자리이상 20자 이하여야합니다.");
        }
    }

}
