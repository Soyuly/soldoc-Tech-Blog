package com.soldoc.tech.domain.post.web.api;


import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.*;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    //@RequiredArgsConstructor: final로 선언된 필드 자동 생성자 생성
    private final PostService postService;

    //가장 최근 게시물 순으로 post 테이블 내 title, body, author, modifiedDate 확인
    @GetMapping("/api")
    public List<PostListResponseDto> findAllContent() {
        return postService.findAllContent();
    }

    @GetMapping("/api/contents/search")
    public List<PostListResponseDto> search(@RequestParam(value="word") String word){
        return postService.search(word);
    }

    //객체를 저장
    //Entity를 직접 이용하지 않고, DTO(PostSaveRequestDto)를 만들어, 객체 전달
    @PostMapping("/api/contents")
    public Long save(@RequestBody PostSaveRequestDto requestDto) {
        return postService.save(requestDto);
    }

    @PutMapping("/api/contents/{id}")
    public boolean update(@RequestBody PostUpdateRequestDto requestDto, @PathVariable Long id){
        return postService.update(requestDto, id);
    }

    @GetMapping("/api/contents/{id}")
    public PostResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    @DeleteMapping("/api/contents/{id}")
    public Long delete(@PathVariable Long id){
        return postService.delete(id);
    }

    @PostMapping("/api/contents/{id}/addLike")
    public boolean addLike(@PathVariable Long id){
        return postService.addLike(id);
    }

    @PostMapping("/api/contents/{id}/deleteLike")
    public boolean deleteLike(@PathVariable Long id){
        return postService.deleteLike(id);
    }

    @PostMapping("/api/yy")
    public long create(@RequestBody PostSaveRequestDto postSaveRequestDto, @RequestBody KeywordSearchReqDto keywordSearchReqDto){
        return postService.create(postSaveRequestDto, keywordSearchReqDto);
    }

}
