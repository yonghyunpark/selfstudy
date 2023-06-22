package com.selfstudy.service;

import com.selfstudy.domain.Exam;
import com.selfstudy.repository.exam.ExamRepository;
import com.selfstudy.request.ExamCreate;
import com.selfstudy.response.ExamResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public void enroll(ExamCreate examCreate) {
        Exam exam = new Exam(examCreate.getName());
        examRepository.save(exam);
    }

    public List<ExamResponse> findAllDESC() {
        return examRepository.findAllDESC().stream()
                .map(ExamResponse::new)
                .collect(Collectors.toList());
    }
}
