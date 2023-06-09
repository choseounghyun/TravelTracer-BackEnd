package com.project.travelTracer.comment.exception;

import com.project.travelTracer.global.exception.BaseException;
import com.project.travelTracer.global.exception.BaseExceptionType;

public class CommentException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public CommentException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }
    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
