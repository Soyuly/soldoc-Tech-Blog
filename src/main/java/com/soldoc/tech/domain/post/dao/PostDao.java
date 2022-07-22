package com.soldoc.tech.domain.post.dao;

import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {



    Page<Post> findAllByDeleteStatusNotOrderByIdDesc(String delete_status, PageRequest pageRequest);

    List<Post> findAllByUserId(Long userId);

    Page<PostListResponseDto> findByTitleContainingOrBodyContaining(String title_word, String body_word, PageRequest pageRequest);
    boolean existsByTitleContainingOrBodyContaining(String title_word, String body_word, PageRequest pageRequest);
    Page<Post> findAllByPostKeywordsName(String word, PageRequest pageRequest);

    boolean existsByPostKeywordsName(String word, PageRequest pageRequest);

    List<Post> findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(short likeCount);

    List<Post> findAllByPostKeywords_Name(String word);


}