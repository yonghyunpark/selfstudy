package com.selfstudy.service;

import com.selfstudy.domain.Post;
import com.selfstudy.exception.PostNotFound;
import com.selfstudy.repository.PostRepository;
import com.selfstudy.request.PostCreate;
import com.selfstudy.request.PostEdit;
import com.selfstudy.request.PostSearch;
import com.selfstudy.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("문제 작성")
    void test1() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .ask("문제")
                .answer("답")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().get(0);
        assertThat(post.getAsk()).isEqualTo("문제");
        assertThat(post.getAnswer()).isEqualTo("답");

    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        //given
        Post requestPost = Post.builder()
                .ask("이게 뭘까요")
                .answer("이것입니다")
                .build();
        postRepository.save(requestPost);

        // 클라이언트 요구사항
        // JSON 응답에서 ask값 길이를 최대 10글자로 해주세요.
        // 이런 처리는 클라이언트에서 하는게 좋지만 서버에서 해야할일이 생겼을 때

        //when
        PostResponse postResponse = postService.get(requestPost.getId());

        //then
        assertNotNull(postResponse);
        assertThat(postResponse.getAsk()).isEqualTo("이게 뭘까요");
        assertThat(postResponse.getAnswer()).isEqualTo("이것입니다");
    }

    /*@Test
    @DisplayName("글 모두 조회")
    void test3() {
        //given
        postRepository.saveAll(List.of(
                Post.builder()
                        .ask("문1")
                        .answer("답1")
                        .build(),
                Post.builder()
                        .ask("문2")
                        .answer("답2")
                        .build()
        ));

        //when
        List<PostResponse> postList = postService.getList(1);

        //then
        assertEquals(2L, postList.size());
    }*/

    /*@Test
    @DisplayName("글 1페이지 조회")
    void test4() {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                // 저장 후 조회
                        .mapToObj(i -> {
                             return Post.builder()
                                    .ask(i + "번 문제")
                                    .answer(i + "번 답")
                                    .build();
                        })
                                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC,("id"));

        //when
        List<PostResponse> postList = postService.getList(pageable);

        //then
        assertEquals(5L, postList.size());
        assertThat(postList.get(0).getAsk()).isEqualTo("30번 문제");
        assertThat(postList.get(4).getAsk()).isEqualTo("26번 문제");
    }*/

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() {
        //given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                            .ask(i + "번 문제")
                            .answer(i + "번 답")
                            .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // sql => select, limit, offset 같은 기본 쿼리 숙지!

        //when
        List<PostResponse> postList = postService.getList(postSearch);

        //then
        assertEquals(10L, postList.size());
        assertThat(postList.get(0).getAsk()).isEqualTo("30번 문제");
    }

    @Test
    @DisplayName("글 문제 수정")
    void test4() {
        //given
        Post post = Post.builder()
                .ask("문제")
                .answer("답")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .ask("문제 수정")
                .answer("답") // 수정하지 않더라도 그대로 보내기
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(changePost.getAsk()).isEqualTo("문제 수정");
        assertThat(changePost.getAnswer()).isEqualTo("답");
    }

    @Test
    @DisplayName("글 답 수정")
    void test5() {
        //given
        Post post = Post.builder()
                .ask("문제")
                .answer("답")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .ask("문제")
                .answer("답 수정") // 수정하지 않더라도 그대로 보내기
                .build();

        //when
        postService.edit(post.getId(), postEdit);

        //then
        Post changePost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));

        assertThat(changePost.getAsk()).isEqualTo("문제");
        assertThat(changePost.getAnswer()).isEqualTo("답 수정");
    }

    @Test
    @DisplayName("글 삭제")
    void test6() {
        //given
        Post post = Post.builder()
                .ask("문제")
                .answer("답")
                .build();

        postRepository.save(post);

        //when 삭제했을때
        postService.delete(post.getId());

        //then 검증
        assertThat(postRepository.count()).isEqualTo(0l);
    }

    @Test
    @DisplayName("글 1개 조회 실패")
    void test7() {
        // given
        Post post = Post.builder()
                .ask("이게 뭘까요")
                .answer("이것입니다")
                .build();
        postRepository.save(post);

        // expected // 예외 감지
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 수정 실패")
    void test8() {
        // given
        Post post = Post.builder()
                .ask("이게 뭘까요")
                .answer("이것입니다")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .ask("이게 뭘?")
                .answer("까요")
                .build();

        // expected // 예외 감지
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId() + 1L, postEdit);
        });
    }

    @Test
    @DisplayName("글 삭제 실패")
    void test9() {
        // given
        Post post = Post.builder()
                .ask("이게 뭘까요")
                .answer("이것입니다")
                .build();
        postRepository.save(post);

        // expected // 예외 감지
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }
}