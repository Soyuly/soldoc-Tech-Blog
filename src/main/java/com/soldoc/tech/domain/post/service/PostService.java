package com.soldoc.tech.domain.post.service;

import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.*;
import com.soldoc.tech.domain.postkeyword.dao.PostkeywordDao;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//postsRepository결과의 값(Post의 Stream)을 map을 통해 PostListResponseDto로 변환->List로 반환하는 메소드
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostDao postDao;
    private final PostkeywordDao postkeywordDao;
    private final KeywordDao keywordDao;


    @Transactional
    public List<PostListResponseDto> findAllContent() {
        return postDao.findAllDesc().stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public List<PostListResponseDto> search(@RequestParam(value="word") String word) {
        return postDao.findAllSearch(word).stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }



    //Service: 트랜잭션과 도메인(:=레파지토리,다오..) 간 순서만 보장.
    //Service단에서 Post DTO를 사용해서 / **다오에 객체를 전달.**
    @Transactional
    public Long save(PostSaveRequestDto requestDto){
        return postDao.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public boolean update(PostUpdateRequestDto requestDto, Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        post.update(requestDto.getTitle(), requestDto.getBody());
        return true;
    }

    @Transactional
    public PostResponseDto findById(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        return new PostResponseDto(post.getTitle(), post.getPostKeywords());
    }




    @Transactional
    public Long delete(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        postDao.delete(post);
        return id;
    }

    @Transactional
    public boolean addLike(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        post.addLike();
        return true;
    }

    @Transactional
    public boolean deleteLike(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        short curLikeCount = post.getLikeCount();
        if(curLikeCount <= 0){
            return false;
        }
        post.deleteLike();
        return true;
    }

    @Transactional
    public long create(PostSaveRequestDto postSaveRequestDto, KeywordSearchReqDto keywordSearchReqDto){

        Post p3 = postDao.save(postSaveRequestDto.toEntity());
        Long id = p3.getId();

        System.out.println(">>>>>" + id);

        Keyword k1 = keywordDao.save(keywordSearchReqDto.toEntity());

        Long k_id = k1.getId();

        return k_id;
//
////        String firstName = "";
////        name.forEach((String na)-> );
//        KeywordSearchReqDto k = KeywordSearchReqDto.builder()
//                .name(name)
//                .build();
//
//        Keyword k2 = k.toEntity();
//        Keyword k3 = keywordDao.save(k2);
//
//
//
//        PostKeyword postKeyword = PostKeyword.builder()
//                .post(p3)
//                .keyword(k3)
//                .build();
//
//        return postkeywordDao.save(postKeyword);


    }
    @Transactional
    public Post findByPostId(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        return post;
    }









}
