package com.selfstudy.response;

import com.selfstudy.domain.Post;
import lombok.Builder;
import lombok.Getter;

// 응답 전용 클래스
@Getter
public class PostResponse {

    private final Long id;
    private final String ask;
    private final String answer;

    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.ask = post.getAsk();
        this.answer = post.getAnswer();
    }

    @Builder
    public PostResponse(Long id, String ask, String answer) {
        this.id = id;
        this.ask = ask.substring(0, Math.min(ask.length(), 10));
        this.answer = answer;
    }
}
