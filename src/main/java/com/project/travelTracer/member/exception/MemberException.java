package com.project.travelTracer.member.exception;

import com.project.travelTracer.global.exception.BaseException;
import com.project.travelTracer.global.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
