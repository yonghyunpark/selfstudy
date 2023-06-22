package com.selfstudy.response;

import com.selfstudy.domain.Exam;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExamResponse {

    private Long id;
    private final String name;

    public ExamResponse(Exam exam) {
        this.id = exam.getId();
        this.name = exam.getName();
    }

    @Builder
    public ExamResponse(String name) {
        this.id = id;
        this.name = name;
    }
}
