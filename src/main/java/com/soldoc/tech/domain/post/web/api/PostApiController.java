package com.soldoc.tech.domain.post.web.api;


import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.*;
import com.soldoc.tech.common.PostApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor: final로 선언된 필드 자동 생성자 생성
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostApiController {
    private final PostService postService;

//    @GetMapping("/posts")
//    public List<PostListResponseDto> findAllContent() {
//        return postService.findAllContents();
//    }


    @PostMapping("/post")
    public PostApiResponse<Object>  create(@RequestBody PostVO postAllRequestDto){
       return postService.create(postAllRequestDto);
}

    @GetMapping()
    public Page<PostListResponseDto> getAllPostPageWithQuery(@RequestParam("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 4);
        return postService.getAllPostPage(pageRequest);
    }


    @GetMapping("/posts/search")
    public PostApiResponse<Object> search(@RequestParam("word") String word){
        PageRequest pageRequest = PageRequest.of(0,4);
        return postService.search(word, pageRequest);
    }

    @GetMapping("/posts/keyword")
    public PostApiResponse<Object> keyword(@RequestParam("word") String word){
        PageRequest pageRequest = PageRequest.of(0,4);
        return postService.keywordSearch(word, pageRequest);
    }




    //해당 게시물을 방문
    @GetMapping("/post/{id}")
    public PostApiResponse<Object> findById(@PathVariable Long id){
        return postService.findByPostId(id);
    }

    //해당 게시물의 내용 수정
    @PutMapping("/post/{id}")
    public PostApiResponse<Object> update(@RequestBody PostUpdateRequestDto requestDto, @PathVariable Long id){
        return postService.update(requestDto, id);
    }

    //해당 게시물 삭제
    @DeleteMapping("/post/{id}")
    public PostApiResponse<Object> delete(@PathVariable Long id){
        return postService.delete(id);
    }

    @PostMapping("/v1/post/{id}/restore")
    public PostApiResponse<Object> restore(@PathVariable Long id ){
        return postService.restore(id);
    }


    // 해당 게시물 좋아요 클릭
    // TODO 프론트 쪽 : 유저가 좋아요를 눌렀을 경우 +1 이미 눌렀을 경우 -1
    @PostMapping("/post/{id}/addlike")
    public PostApiResponse<Object> addLike(@PathVariable Long id){
        return postService.addLike(id);
    }

    //해당 게시물 좋아요 클릭하지 않음
    @PostMapping("/post/{id}/deletelike")
    public PostApiResponse<Object> deleteLike(@PathVariable Long id){
        return postService.deleteLike(id);
    }


    @GetMapping("/post/recommend")
    public PostApiResponse<Object> postRecommend(){
        return postService.postRecommend();
    }

    @RequestMapping(value="/home", method=RequestMethod.GET)
    public int checkEmail() throws SQLException, Exception {
        return 5;
    }

}
