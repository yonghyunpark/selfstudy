package com.selfstudy.request;

import com.selfstudy.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank; //build.gradle에 validation 의존 추가

@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "문제를 입력하십시오.")
    private String ask;

    @NotBlank(message = "답을 입력하십시오.")
    private String answer;

    @Builder
    public PostCreate(String ask, String answer) {
        this.ask = ask;
        this.answer = answer;
    }

    public void validate() {
        if (ask.contains("난이도 상")) {
            throw new InvalidRequest("ask", "문제에 '난이도 상'을 포함할 수 없습니다.");
        }
    }

    // <빌더의 장점>
    // 가독성이 좋다.
    // 값 생성에 대한 유연함
    // 필요한 값만 받을 수 있다. (빌더가 없다면 오버로딩해서 코드를 짜야함 => 지저분함) // 오버로딩 가능한 조건 학습
    // 객체의 불변성(중요한 장점)
}
