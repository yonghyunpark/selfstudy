package com.selfstudy.controller;

import com.selfstudy.request.ExamCreate;
import com.selfstudy.request.PostCreate;
import com.selfstudy.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ExamController {

    private final ExamService examService;

    @PostMapping("/api/exams")
    public void enroll(@RequestBody ExamCreate request) {
        examService.enroll(request);
    }
}
