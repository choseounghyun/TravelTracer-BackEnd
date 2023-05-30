package com.project.travelTracer.global.file.exception;

import com.project.travelTracer.global.exception.BaseException;
import com.project.travelTracer.global.exception.BaseExceptionType;

public class FileException extends BaseException {

    private BaseExceptionType exceptionType;


    public FileException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }


}
