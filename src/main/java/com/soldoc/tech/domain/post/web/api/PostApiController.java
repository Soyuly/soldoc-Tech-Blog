package com.soldoc.tech.domain.post.web.api;


import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor: final로 선언된 필드 자동 생성자 생성
@RequiredArgsConstructor
@RestController
public class PostApiController {
    //좋아요 값 그대로 보내 주게, 특정 게시글 방문 후 조회수 추가  //
    //post할때 결과값을 다시 리턴해주는게 더 좋다 (클라이언트에서 그대로 상태변화를 주면 되니까)
    //삭제(deleteAt 복원가능하게), 업데이트 예외처리(특정 데이터 안넣어준다면?)
    //findAll 페이지 처리
    //theme CRUD : 관리자 쪽에서 테마도 추가,삭제,수정 할 수 있도록 (5개)


    //객체를 저장
    //Entity를 직접 이용하지 않고, DTO(PostSaveRequestDto)를 만들어, 객체 전달


    private final PostService postService;

    @GetMapping("/api")
    public List<PostListResponseDto> findAllContent() {
        return postService.findAllContent();
    }

    @PostMapping("/api/contents")
    public void create(@RequestBody PostVO postAllRequestDto){
        postService.create(postAllRequestDto);
    }

    @GetMapping("/api/contents/search")
    public List<PostListResponseDto> search(@RequestParam(value="word") String word){
        return postService.search(word);
    }



    //해당 게시물을 방문
    @GetMapping("/api/contents/{id}")
    public PostListResponseDto findById(@PathVariable Long id){
        return postService.findById(id);
    }

    //해당 게시물의 내용 수정
    @PutMapping("/api/contents/{id}")
    public boolean update(@RequestBody PostUpdateRequestDto requestDto, @PathVariable Long id){
        return postService.update(requestDto, id);
    }

    //해당 게시물 삭제
    @DeleteMapping("/api/contents/{id}")
    public DeleteResponseDto delete(@PathVariable Long id){
        return postService.delete(id);
    }


    //해당 게시물 좋아요 클릭
    @PostMapping("/api/contents/{id}/addLike")
    public short addLike(@PathVariable Long id){
        return postService.addLike(id);
    }

    //해당 게시물 좋아요 클릭하지 않음
    @PostMapping("/api/contents/{id}/deleteLike")
    public short deleteLike(@PathVariable Long id){
        return postService.deleteLike(id);
    }


}
