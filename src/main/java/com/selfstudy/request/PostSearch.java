package com.selfstudy.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSearch {

    private static final int Max_SIZE = 2000;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size= 10;

    public long getOffset() {
        // 음수값이 나오는걸 방지하기 위해
        return (long) (Math.max(1, page) -1) * Math.min(size, Max_SIZE);
    }

    /*@Builder
    public PostSearch(Integer page, Integer size) {
        this.page = page; //page == null ? 1 : page;
        this.size = size; //size == null ? 10 : size;
    }*/
}
