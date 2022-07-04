package com.soldoc.tech.domain.post.dao;

import com.soldoc.tech.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostDao extends JpaRepository<Post, Long> {
}
