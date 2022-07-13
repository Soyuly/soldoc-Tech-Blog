package com.soldoc.tech.domain.post.dao;

import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.deleteStatus <> 'Y' ORDER BY p.id DESC")
    List<Post> findAllDesc();

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:word% OR p.body LIKE %:word%")
    Page<PostListResponseDto> findAllSearch(String word, PageRequest pageRequest);

    List<Post> findAllByUserId(Long userId);


//    Page<PostListResponseDto> findAllPost(Pageable pageable);
////    Page<PostListResponseDto> findAllPost(PageRequest pageRequest);
}
