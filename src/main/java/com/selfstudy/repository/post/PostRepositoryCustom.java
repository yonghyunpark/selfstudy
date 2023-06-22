package com.selfstudy.repository.post;

import com.selfstudy.domain.Post;
import com.selfstudy.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
