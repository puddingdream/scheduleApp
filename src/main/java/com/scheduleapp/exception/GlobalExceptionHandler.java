package com.scheduleapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //모든곳에서 던지는 에러 받기
public class GlobalExceptionHandler {  // 국룰닉네임

    // 유저가틀림
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleFailed(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // null조회
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleNotFound(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // 유저가틀림
    @ExceptionHandler(ClientMissException.class)
    public ResponseEntity<String> handleMissClient(ClientMissException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // null조회
    @ExceptionHandler(NullException.class)
    public ResponseEntity<String> handleNullException (NullException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // 권한 없음
    @ExceptionHandler(UnauthorizedCommentException.class)
    public ResponseEntity<String> handleUnauthorized(UnauthorizedCommentException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    //@Valid 오류
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidException (MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
