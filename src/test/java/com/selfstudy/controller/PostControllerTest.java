package com.selfstudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.selfstudy.domain.Post;
import com.selfstudy.repository.PostRepository;
import com.selfstudy.request.PostCreate;
import com.selfstudy.request.PostEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청에 대한 출력")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .ask("문제")
                .answer("답")
                .build();

        String json = objectMapper.writeValueAsString(request); // JSON형태로 가공

        //expected
        mockMvc.perform(post("/posts") // mockMvc는 Content-Type을 application/JSON 형태로 보냄
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("")) // 빈 객체로 넘어옴
                .andDo(print());
    }

    @Test
    @DisplayName("ask, answer는 필수")
    void test2() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .ask("")
                .answer("답")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/posts") //mockMvc는 Content-Type을 application/JSON 형태로 보냄
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.ask").value("문제를 입력하십시오."))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .ask("문제")
                .answer("답")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/posts") //mockMvc는 Content-Type을 application/JSON 형태로 보냄
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().get(0);
        assertThat(post.getAsk()).isEqualTo("문제");
        assertThat(post.getAnswer()).isEqualTo("답");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .ask("이게 뭘까요1234555555")
                .answer("이것입니다")
                .build();
        postRepository.save(post);

        //expected (when + then)
        mockMvc.perform(get("/posts/{postId}", post.getId()) // Id를 파라미터로 받음
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.ask").value("이게 뭘까요1234"))
                .andExpect(jsonPath("$.answer").value("이것입니다"))
                .andDo(print());
    }

    /*@Test
    @DisplayName("글 모두 조회")
    void test5() throws Exception {
        //given
        Post post1 = postRepository.save(Post.builder()
                .ask("문1")
                .answer("답1")
                .build());


        Post post2 = Post.builder()
                .ask("문2")
                .answer("답2")
                .build();
        postRepository.save(post2);


        //expected
        *//**
         * 단건 => {id:~, ask:~ ...}
         * 리스트 => [{id:~, ask:~ ...}, {id:~, ask:~ ...} ...]
         *//*
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].ask").value("문1"))
                .andExpect(jsonPath("$[0].answer").value("답1"))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].ask").value("문2"))
                .andExpect(jsonPath("$[1].answer").value("답2"))
                .andDo(print());

    }*/

    @Test
    @DisplayName("글 1페이지 조회")
    void test5() throws Exception {
        // given
        List<Post> postList = IntStream.range(1, 31)
                .mapToObj(x -> Post.builder()
                        .ask(x + "번 문제")
                        .answer(x + "번 답")
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(postList);

        //expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                //.andExpect(jsonPath("$[0].id").value(30L))
                .andExpect(jsonPath("$[0].ask").value("30번 문제"))
                .andExpect(jsonPath("$[0].answer").value("30번 답"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫페이지를 가져온다?")
    void test6() throws Exception {
        // given
        List<Post> postList = IntStream.range(1, 31)
                .mapToObj(x -> Post.builder()
                        .ask(x + "번 문제")
                        .answer(x + "번 답")
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(postList);

        //expected
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                //.andExpect(jsonPath("$[0].id").value(30L))
                .andExpect(jsonPath("$[0].ask").value("30번 문제"))
                .andExpect(jsonPath("$[0].answer").value("30번 답"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 문제 수정")
    void test7() throws Exception {
        // given
        Post post = Post.builder()
                .ask("문제")
                .answer("답")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .ask("문제 수정")
                .answer("답") // 수정하지 않더라도 그대로 보내기
                .build();

        //expected
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("글 삭제")
    void test8() throws Exception {
        //given
        Post post = Post.builder()
                .ask("문제")
                .answer("답")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 글 조회")
    void test9() throws Exception {
        //given
        Post post = Post.builder()
                .ask("문")
                .answer("답")
                .build();
        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts/{postId}", post.getId() + 1L)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 글 수정")
    void test10() throws Exception {
        //given
        Post post = Post.builder()
                .ask("문")
                .answer("답")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .ask("문ed")
                .answer("답ed")
                .build();

        //expected
        mockMvc.perform(patch("/posts/{postId}",post.getId() + 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성시 '난이도 상' 문구를 포함할 수 없다.")
    void test11() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .ask("난이도 상 : 문제")
                .answer("답")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/posts") //mockMvc는 Content-Type을 application/JSON 형태로 보냄
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}