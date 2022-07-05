package com.soldoc.tech.domain.post.web.api;


import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.post.web.dto.PostResponseDto;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.post.web.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    //@RequiredArgsConstructor: final로 선언된 필드 자동 생성자 생성
    private final PostService postService;

    //내림차순으로 post 테이블 내 title, author, modifiedDate를 확인
    @GetMapping("/")
    public List<PostListResponseDto> findAllContent() {
        return postService.findAllContent();
    }

    @GetMapping("/contents/search")
    public List<PostListResponseDto> search(@RequestParam(value="keyword") String keyword){
        return postService.search(keyword);
    }

    //객체를 저장
    //Entity를 직접 이용하지 않고, DTO(PostSaveRequestDto)를 만들어, 객체 전달
    @PostMapping("/contents")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/contents/{id}")
    public Long update(@RequestBody PostUpdateRequestDto requestDto, @PathVariable Long id){
        return postService.update(requestDto, id);
    }

    @GetMapping("/contents/{id}")
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @DeleteMapping("/contents/{id}")
    public Long delete(@PathVariable Long id){
        return postService.delete(id);
    }

}
