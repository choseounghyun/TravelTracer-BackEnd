package com.project.travelTracer.Image.exception;

import com.project.travelTracer.global.exception.BaseException;
import com.project.travelTracer.global.exception.BaseExceptionType;

public class ImageException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public ImageException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
