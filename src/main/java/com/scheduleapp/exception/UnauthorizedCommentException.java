package com.scheduleapp.exception;

public class UnauthorizedCommentException extends RuntimeException {
    public UnauthorizedCommentException(String message) {
        super(message);
    }
}
