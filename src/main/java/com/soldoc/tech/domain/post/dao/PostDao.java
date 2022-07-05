package com.soldoc.tech.domain.post.dao;

import com.soldoc.tech.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Post> findAllDesc();

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.body LIKE %:keyword%")
    List<Post> findAllSearch(String keyword);
}