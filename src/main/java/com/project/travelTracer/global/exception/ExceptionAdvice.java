package com.project.travelTracer.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//코드 전역 예외 처리
@RestControllerAdvice  //컨트롤러에서 발생하는 예외 전역 처리를 위한 코드
@Slf4j
public class ExceptionAdvice {

    //BaseException 타입의 예외를 처리
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

    //ExceptionDTO는 예외의 오류 코드를 전달하기 위한 데이터 전송 객체
    //errorCode 필드를 가지고 있으며, 생성자와 게터를 포함
    @Data
    @AllArgsConstructor
    static class ExceptionDTO {
        private  Integer errorCode;
    }

}
