package com.selfstudy.request;

import com.selfstudy.exception.InvalidRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class ExamCreate {

    @NotBlank(message = "내 시험을 등록하십시오.")
    private String name;

    @Builder
    public ExamCreate(String name) {
        this.name = name;
    }

    /*public void validate() {
        if (name.contains("난이도 상")) {
            throw new InvalidRequest("ask", "문제에 '난이도 상'을 포함할 수 없습니다.");
        }
    }*/
}
