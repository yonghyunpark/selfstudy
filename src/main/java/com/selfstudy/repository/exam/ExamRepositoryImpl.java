package com.selfstudy.repository.exam;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selfstudy.domain.Exam;
import com.selfstudy.domain.QExam;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExamRepositoryImpl implements ExamRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Exam> findAllDESC() {
        return jpaQueryFactory.selectFrom(QExam.exam)
                .orderBy(QExam.exam.id.desc()).fetch();
    }
}
