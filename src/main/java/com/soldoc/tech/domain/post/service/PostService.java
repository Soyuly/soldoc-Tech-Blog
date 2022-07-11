package com.soldoc.tech.domain.post.service;

import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.*;
import com.soldoc.tech.domain.postkeyword.dao.PostkeywordDao;
import com.soldoc.tech.domain.postkeyword.dto.PostKeywordSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import com.soldoc.tech.domain.theme.dao.ThemeDao;
import com.soldoc.tech.domain.theme.model.Theme;
import com.soldoc.tech.oauth.api.entity.user.User;
import com.soldoc.tech.oauth.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

//postsRepository결과의 값(Post의 Stream)을 map을 통해 PostListResponseDto로 변환->List로 반환하는 메소드
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostDao postDao;
    private final PostkeywordDao postkeywordDao;
    private final KeywordDao keywordDao;
    private final UserService userService;
    private final ThemeDao themeDao;


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

//    @Transactional
//    public PostResponseDto findByUserId(Long userId){
//        List<Post> post = postDao.findAllByUserId(userId);
//        return new PostResponseDto(post.getTitle(), post.getPostKeywords());
//    }




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
    public void create(PostVO postAllRequestDto){
        // 현재 접속한 계정 헤더의 Authroization을 읽어온다.
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder
                .getContext()
                        .getAuthentication()
                        .getPrincipal();

        User user = userService.getUser(principal.getUsername());

        //Post 객체 생성 (Dto -> Entity화)
        Post post = postDao.save(PostSaveRequestDto.builder()
                .title(postAllRequestDto.getTitle())
                .body(postAllRequestDto.getBody())
                .author(user.getUsername())
                .user(user)
                .build().toEntity());

        Theme themeEntity = themeDao.findByName(postAllRequestDto.getTheme());
        //Keyword 객체 생성  & PostKeyword 객체 생성
        for(String keywordName: postAllRequestDto.getKeywords()){
            Keyword keyword = keywordDao.save(KeywordSaveRequestDto.builder()
                    .theme(themeEntity)
                    .name(keywordName)
                    .build().toEntity());

            PostKeyword postKeyword = postkeywordDao.save(PostKeywordSaveRequestDto.builder()
                    .post(post)
                    .keyword(keyword)
                    .build().toEntity());
        }

        //TODO - return HTTP.CREATE
    }


    @Transactional
    public Post findByPostId(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        return post;
    }









}
