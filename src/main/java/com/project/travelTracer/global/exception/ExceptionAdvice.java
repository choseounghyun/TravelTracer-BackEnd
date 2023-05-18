package com.project.travelTracer.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseEx(BaseException e) {
        log.error("baseException errorMessage(): {}", e.getExceptionType().getErrorMessage());
        log.error("baseException errorCode(): {}", e.getExceptionType().getErrorCode());

        return new ResponseEntity(new ExceptionDTO(e.getExceptionType().getErrorCode()), e.getExceptionType().getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleMemberEx(Exception e) {
        e.printStackTrace();;
        return new ResponseEntity(HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class ExceptionDTO {
        private  Integer errorCode;
    }

}
