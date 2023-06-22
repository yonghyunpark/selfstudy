package com.selfstudy.service;

import com.selfstudy.domain.Post;
import com.selfstudy.exception.PostNotFound;
import com.selfstudy.repository.post.PostRepository;
import com.selfstudy.request.PostCreate;
import com.selfstudy.request.PostEdit;
import com.selfstudy.request.PostSearch;
import com.selfstudy.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    // 글 등록, 단건 조회, 리스트 조회
    // CRUD -> Create, Read, Update, Delete

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .ask(postCreate.getAsk())
                .answer(postCreate.getAnswer())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound()); // 글이 없을 때 예외 처리 / = PostNotFound::new

        return PostResponse.builder()
                .id(post.getId())
                .ask(post.getAsk())
                .answer(post.getAnswer())
                .build();

        /**
         * Post 엔티티를 PostResponse로 변환했는데, 이 과정을 Service Layer에서 하는게 맞는건가? 흠
         * Controller -> WebService -> Repository 로 나눔  // 여기서 WebService쪽에서 진행하는데, 그냥 Service 하나로만 통합하는 경우도 있음
         *              + Service
         */
    }

    // 글이 너무 많은 경우 => 비용이 너무 많이 발생
    // 글이 => 100,000,000 => DB 글 모두 조회하는 경우 => DB가 뻗을 수 있다.
    // DB => APP 서버로 전달하는 시간, 트래픽 비용 등이 많이 발생할 수 있다.

    public List<PostResponse> getList(PostSearch postSearch) {

        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        /*PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

        PostEditor postEditor = postEditorBuilder
                .ask(postEdit.getAsk())
                .answer(postEdit.getAnswer())
                .build();*/

        post.edit(postEdit.getAsk(), postEdit.getAnswer());
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound());

        postRepository.delete(post);
    }
}
