package com.selfstudy.exception;

import lombok.Getter;

/**
 *  satus -> 400
 */

@Getter
// ex) 글 작성 api => @NotBlank와 같이 어노테이션으로 해결할 수 없는 좀 더 복잡한 예외를 처리
public class InvalidRequest extends SelfStudyException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    private String fieldName;
    private String message;

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
