package com.selfstudy.repository;

import com.selfstudy.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

// PostRepositoryCustom을 구현하고 있는 PostRepositoryImpl의 기능들이 자동으로 주입됨
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
