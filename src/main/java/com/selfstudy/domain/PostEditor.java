package com.selfstudy.domain;

import lombok.Builder;
import lombok.Getter;

// 필드가 늘어나면 Post의 edit메서드만으로 해결할 때 코드 길이가 길어짐
// 수정해야할 필드만 좁혀서 선언할 수 있어서 구분이 잘됨(제한 포인트를 둠)
//
@Getter
public class PostEditor {

    private final String ask;
    private final String answer;

    @Builder
    public PostEditor(String ask, String answer) {
        this.ask = ask;
        this.answer = answer;
    }
}
