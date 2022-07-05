package com.soldoc.tech.domain.like.dao;

import com.soldoc.tech.domain.like.model.Like;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;



@Transactional
public interface LikeDao extends JpaRepository<Like, Long> {
    //findByUserAndPost() 라는 이름의 추상 메서드를 만든다.
    Optional<Like> findByUserAndPost(User user, Post post);
}
