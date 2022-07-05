package com.soldoc.tech.domain.like.service;//package com.soldoc.tech.domain.keyword.service;

import com.soldoc.tech.domain.like.dao.LikeDao;
import com.soldoc.tech.domain.like.model.Like;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Transactional
@RequiredArgsConstructor
@Service
public class LikeService {
    private LikeDao likeDao;
    private PostDao postDao;

    public boolean addLike(User user, Long postId){
        Post post = postDao.findById(postId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        //좋아요 중복 확인
        if(isNotAlreadyLike(user, post)){
            likeDao.save(new Like(post, user));
            return true;
        }
        return false;


    }
    //사용자가 이미 좋아요한 게시물인지 확인
    //.isPresent: 좋아요한 게시물: 1  / 좋아요하지 않은 게시물: 0
    private boolean isNotAlreadyLike(User user, Post post){
        return likeDao.findByUserAndPost(user, post).isPresent();
    }
}
