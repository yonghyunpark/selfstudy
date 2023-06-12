package com.selfstudy.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ask;

    // 자바에서는 String형태로 있지만, DB에서는 LongText 형태로 저장
    @Lob
    private String answer;

    @Builder
    public Post(String ask, String answer) {
        this.ask = ask;
        this.answer = answer;
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .ask(ask)
                .answer(answer);
    }

    // PostEditor 라는 하나의 인자만 받는 메서드로 개선을 시킴
    public void edit(String ask, String answer) {
        this.ask =ask;
        this.answer = answer;
    }
}
