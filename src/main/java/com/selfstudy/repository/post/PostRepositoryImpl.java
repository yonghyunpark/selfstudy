package com.selfstudy.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.selfstudy.domain.Post;
import com.selfstudy.domain.QPost;
import com.selfstudy.request.PostSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearch postSearch) {
        // select, from이 같이 적용
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(postSearch.getSize()) // limit - 출력 갯수
                .offset(postSearch.getOffset()) // offset - 시작 위치
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
