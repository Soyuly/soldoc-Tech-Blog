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
import com.soldoc.tech.common.PostApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

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
    public PostApiResponse<Object> create(PostVO postAllRequestDto){
        // 현재 접속한 계정 헤더의 Authroization을 읽어온다.
        org.springframework.security.core.userdetails.User principal;
        try {
           principal = (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();
        } catch(ClassCastException e){
            return PostApiResponse.notLogin();
        }



        User user = userService.getUser(principal.getUsername());

        //Post 객체 생성 (Dto -> Entity화)
        Post post = postDao.save(PostSaveRequestDto.builder()
                .title(postAllRequestDto.getTitle())
                .body(postAllRequestDto.getBody())
                .author(user.getUsername())

                .user(user)
                .build().toEntity());

        Theme themeEntity = themeDao.findDistinctByName(postAllRequestDto.getTheme());
        System.out.println(themeEntity.getName());
        //Keyword 객체 생성  & PostKeyword 객체 생성
        for(Long keywordId: postAllRequestDto.getKeywords()){
            Keyword keyword = keywordDao.findById(keywordId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 키워드입니다."));

            PostKeyword postKeyword = postkeywordDao.save(PostKeywordSaveRequestDto.builder()
                    .post(post)
                    .keyword(keyword)
                    .name(keyword.getName())
                    .build().toEntity());
        }
        return PostApiResponse.created("created_post",post.toDTO());

    }

    @Transactional
    public Page<PostListResponseDto> getAllPostPage(PageRequest pageRequest) {
        return postDao.findAll(pageRequest).map(PostListResponseDto::new);

//        System.out.println("첫 시작 페이지  :  " + start_page);
//        System.out.println("페이지 내 게시물 갯수  : " + p.getNumberOfElements());
//        System.out.println("제공되는 총 페이지 수  : " + p.getTotalPages());
//        System.out.println("총 데이터의 수  : " + p.getTotalElements());
    }




    @Transactional
    public PostApiResponse<Object> search(String word, PageRequest pageRequest) {
        if(word.isEmpty()){
            return PostApiResponse.requestSearch();
        }

        if(checkPostByTitle(word, pageRequest).getHeader().getCode()== 200){
            return PostApiResponse.success("find", postDao.findByTitleContaining(word, pageRequest));
        }else if(checkPostByBody(word, pageRequest).getHeader().getCode() == 200){
            return PostApiResponse.success("find", postDao.findByBodyContaining(word, pageRequest));
        }

        return PostApiResponse.searchFail();
    }

    @Transactional
    public PostApiResponse<Object> checkPostByTitle(String word, PageRequest pageRequest) {
        if(postDao.findByTitleContaining(word, pageRequest).getContent().isEmpty()){
            return PostApiResponse.searchFail();
        }
        return PostApiResponse.success("word", word);
    }

    @Transactional
    public PostApiResponse<Object> checkPostByBody(String word, PageRequest pageRequest) {
        if(postDao.findByBodyContaining(word, pageRequest).getContent().isEmpty()){
            return PostApiResponse.searchFail();
        }
        return PostApiResponse.success("word", word);
    }


    @Transactional
    public PostApiResponse<Object> keywordSearch(String word, PageRequest pageRequest) {
        if(word.isEmpty()){
            return PostApiResponse.badRequest();
        }
        if(!postDao.keywordExists(word, pageRequest)){
            return PostApiResponse.searchFail();
        }
        return PostApiResponse.success("find", postDao.findAllByPostKeywordsName(word, pageRequest));
    }



    @Transactional
    public PostApiResponse<Object> update(PostUpdateRequestDto requestDto, Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }
        Post post = postDao.findById(id).get();
        boolean isUser = checkUser(post);

        String title = post.getTitle();
        String body = post.getBody();
        if(isUser){

            if(requestDto.getTitle() != null){
                title = requestDto.getTitle();
            }

            if(requestDto.getBody() != null){
                body = requestDto.getBody();
            }
            post.update(title,body);
            return PostApiResponse.success("post",post);
        }

        return PostApiResponse.notAuthor();
    }



//    @Transactional
//    public PostApiResponse<Object> findById(Long id){
//        Optional<PostListResponseDto> post = postDao.findById(id).map(Post::toDTO);
//        return PostApiResponse.success("find", post);
//    }

    @Transactional
    public boolean checkPostExists(Long id) {
        return postDao.existsById(id);
    }

    @Transactional
    public PostApiResponse<Object> delete(Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }

        Post post = postDao.findById(id).get();

        boolean isAuthor = checkUser(post);

        if (!isAuthor){
            return PostApiResponse.notAuthor();
        }

        post.deleteSetStatus();
        post.setDeleteTime();
        return PostApiResponse.success("post_id",post.getId());
    }

    @Transactional
    public PostApiResponse<Object> restore(Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }

        Post post = postDao.findById(id).get();

        boolean isAuthor = checkUser(post);

        if (!isAuthor){
            return PostApiResponse.notAuthor();
        }
        post.restoreStatus();
        post.restoreDeleteTime();
        return PostApiResponse.success("post_id", post.getId());
    }




    @Transactional
    public PostApiResponse<Object> addLike(Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }
        Post post = postDao.findById(id).get();
        return PostApiResponse.success("like_value", post.addLike() );
    }

    @Transactional
    public PostApiResponse<Object> deleteLike(Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }

        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시물입니다."));
        short curLikeCount = post.getLikeCount();
        if(curLikeCount <= 0){
            return PostApiResponse.success("like_value", 0);
        }
        return PostApiResponse.success("like_value", post.deleteLike());
    }


    @Transactional
    public PostApiResponse<Object> findByPostId(Long id){
        if(!checkPostExists(id)){
            return PostApiResponse.hasNoPost();
        }

        Post post = postDao.findById(id).get();
        if(Objects.equals(post.getDeleteStatus(), "Y")){
            return PostApiResponse.hasNoPost();
        }
        return PostApiResponse.success("find", post.toDTO());
    }

    public boolean checkUser(Post post){
        // 현재 접속한 계정 헤더의 Authroization을 읽어온다.
        try {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal();

            User user = userService.getUser(principal.getUsername());

            return Objects.equals(user.getId(), post.getUser().getId());
        } catch (Exception e){
            return false;
        }
    }









}
