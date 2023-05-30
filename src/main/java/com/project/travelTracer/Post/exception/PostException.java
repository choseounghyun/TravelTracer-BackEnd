package com.project.travelTracer.Post.exception;

import com.project.travelTracer.global.exception.BaseException;
import com.project.travelTracer.global.exception.BaseExceptionType;

public class PostException extends BaseException {

    private BaseExceptionType baseExceptionType;


    public PostException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
