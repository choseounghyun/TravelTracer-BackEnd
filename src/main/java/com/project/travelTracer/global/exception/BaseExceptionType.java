package com.project.travelTracer.global.exception;

import org.springframework.http.HttpStatus;

//예외처리
public interface BaseExceptionType {

    int getErrorCode();

    HttpStatus getHttpStatus();

    String getErrorMessage();
}
