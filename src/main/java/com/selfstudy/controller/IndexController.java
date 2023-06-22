package com.selfstudy.controller;

import com.selfstudy.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ExamService examService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("exam", examService.findAllDESC());
        return "index";
    }

    @GetMapping("/exams/enroll")
    public String examsEnroll(){
        return "exams-enroll";
    }

}
