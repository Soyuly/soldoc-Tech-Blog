package com.soldoc.tech.domain.post.service;

import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.post.web.dto.PostResponseDto;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.post.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//postsRepository결과의 값(Post의 Stream)을 map을 통해 PostListResponseDto로 변환->List로 반환하는 메소드
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostDao postDao;

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
    public Long update(PostUpdateRequestDto requestDto, Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        post.update(requestDto.getTitle(), requestDto.getBody());
        return id;
    }

    @Transactional
    public PostResponseDto findById(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        return new PostResponseDto(post.getTitle());
    }

    @Transactional
    public Long delete(Long id){
        Post post = postDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        postDao.delete(post);
        return id;
    }





}
