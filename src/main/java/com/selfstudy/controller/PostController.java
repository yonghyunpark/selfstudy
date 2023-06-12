package com.selfstudy.controller;

import com.selfstudy.exception.InvalidRequest;
import com.selfstudy.request.PostCreate;
import com.selfstudy.request.PostEdit;
import com.selfstudy.request.PostSearch;
import com.selfstudy.response.PostResponse;
import com.selfstudy.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }

    @GetMapping("/posts/{postId}") // postId를 파라미터로 받음(@pathVariable)
    public PostResponse get(@PathVariable Long postId) { // (@PathVariable(name = "postId") Long id)
        // Request 클래스 => PostCreate
        // Response 클래스 => PostResponse

        return postService.get(postId);
        // 응답 클래스를 분리하세요 (서비스 정책에 맞는)
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        postService.edit(postId,postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
}
