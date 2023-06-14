package com.project.travelTracer.Image.exception;

import com.project.travelTracer.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ImageExceptionType implements BaseExceptionType {

    IMAGE_NOT_POUND(700, HttpStatus.NOT_FOUND, "찾으시는 이미지가 없습니다"),
    NOT_AUTHORITY_UPDATE_IMAGE(701, HttpStatus.FORBIDDEN, "이미지를 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_IMAGE(702, HttpStatus.FORBIDDEN, "이미지를 삭제할 권한이 없습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    ImageExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.getHttpStatus();
    }

    @Override
    public String getErrorMessage() {
        return this.getErrorMessage();
    }
}
