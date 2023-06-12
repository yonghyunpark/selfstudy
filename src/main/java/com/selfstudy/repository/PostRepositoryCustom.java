package com.selfstudy.repository;

import com.selfstudy.domain.Post;
import com.selfstudy.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getLst(PostSearch postSearch);
}
