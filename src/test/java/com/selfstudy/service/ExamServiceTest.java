package com.selfstudy.service;

import com.selfstudy.domain.Exam;
import com.selfstudy.repository.exam.ExamRepository;
import com.selfstudy.request.ExamCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ExamServiceTest {

    @Autowired
    ExamService examService;

    @Autowired
    ExamRepository examRepository;

    @Test
    @DisplayName("시험 등록")
    void test1() {
        //given
        ExamCreate examCreate = ExamCreate.builder()
                .name("시험1")
                .build();

        //when
        examService.enroll(examCreate);

        //then
        Exam exam = examRepository.findAll().get(0);
        assertThat(examRepository.count()).isEqualTo(1L);
        assertThat(exam.getName()).isEqualTo("시험1");
    }
}
