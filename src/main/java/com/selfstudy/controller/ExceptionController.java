package com.selfstudy.controller;

import com.selfstudy.exception.InvalidRequest;
import com.selfstudy.exception.SelfStudyException;
import com.selfstudy.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    @ExceptionHandler(MethodArgumentNotValidException.class) //MethodArgumentNotValidException이 발생했을 때만 메서드가 작동하도록 설정
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage()); //문제가 되는 필드와 에러 메시지 출력
        }

        return errorResponse;
    }

    //@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SelfStudyException.class)
    @ResponseBody
    // 스프링에서 기본적으로 제공해주는 객체 로 반환
    public ResponseEntity<ErrorResponse> selfStudyException(SelfStudyException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        // 응답 JSON validation 필드에 key값으로 -> ask : 문제는 '난이도 상'을 포함할 수 없습니다. 가 필요
        /*if (e instanceof InvalidRequest) {
            InvalidRequest invalidRequest = (InvalidRequest) e;
            String fieldName = invalidRequest.getFieldName();
            String message = invalidRequest.getMessage();
            errorResponse.addValidation(fieldName, message);
        }*/

        ResponseEntity<ErrorResponse> responseEntity = ResponseEntity.status(statusCode)
                .body(errorResponse);

        return responseEntity;
    }


    /*@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFound.class)
    @ResponseBody
    public ErrorResponse postNotFound(PostNotFound e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("404")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFound.class)
    @ResponseBody
    public ErrorResponse invalidRequest(PostNotFound e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("404")
                .message(e.getMessage())
                .build();

        return errorResponse;
    }*/
}
