package com.scheduleapp.comment.controller;

import com.scheduleapp.comment.dto.CreateCommentRequest;
import com.scheduleapp.comment.dto.CreateCommentResponse;
import com.scheduleapp.comment.service.CommentService;
import com.scheduleapp.user.dto.SessionUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글", description = "스케줄에 대한 댓글")
@RestController
@RequiredArgsConstructor
public class CommnetController {

    private final CommentService commentService;

    //댓글 생성
    @Operation(summary = "댓글 생성", description = "일정에 댓글을 생성합니다")
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(sessionUser,scheduleId,request));
    }
}
