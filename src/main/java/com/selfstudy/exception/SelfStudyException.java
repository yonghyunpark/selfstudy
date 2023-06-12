package com.selfstudy.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// 추상 클래스로 구현
@Getter
public abstract class SelfStudyException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public SelfStudyException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
