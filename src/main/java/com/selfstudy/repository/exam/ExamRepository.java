package com.selfstudy.repository.exam;

import com.selfstudy.domain.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long>, ExamRepositoryCustom {
}
