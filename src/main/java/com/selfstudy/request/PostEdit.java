package com.selfstudy.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PostEdit {

    @NotBlank(message = "문제를 입력하십시오.")
    private String ask;

    @NotBlank(message = "답을 입력하십시오.")
    private String answer;

    @Builder
    public PostEdit(String ask, String answer) {
        this.ask = ask;
        this.answer = answer;
    }
    // PostCreate와 동일한데 기능이 다르면 코드가 같더라도 분리해주고 시작하는게 좋음(안그러면 줄빠따 맞음)
}
