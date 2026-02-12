package com.scheduleapp.comment.controller;

import com.scheduleapp.comment.dto.*;
import com.scheduleapp.comment.service.CommentService;
import com.scheduleapp.user.dto.SessionUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "댓글", description = "스케줄에 대한 댓글")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @Operation(summary = "댓글 생성", description = "일정에 댓글을 생성합니다")
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser,scheduleId,request));
    }

    //댓글 전체 조회
    @GetMapping("/comments/me")
    public ResponseEntity<List<GetUserAllCommentResponse>>  getAllComments(
            @SessionAttribute(name = "loginUser") SessionUser sessionUser
    ){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComment(sessionUser));
    }

    // 댓글 수정
    @PutMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request
    ){
       return ResponseEntity.status(HttpStatus.OK).body(
               commentService.updateComment(sessionUser,scheduleId,commentId,request));
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody DeleteCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser
    ){
        commentService.deleteComment(scheduleId, commentId, request,sessionUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
