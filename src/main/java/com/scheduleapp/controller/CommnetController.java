package com.scheduleapp.controller;

import com.scheduleapp.dto.CreateCommentRequest;
import com.scheduleapp.dto.CreateCommentResponse;
import com.scheduleapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommnetController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.save(scheduleId,request));
    }
}
