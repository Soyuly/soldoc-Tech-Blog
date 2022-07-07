//package com.soldoc.tech.domain.keyword.web.api;
//
//
//import com.soldoc.tech.domain.keyword.service.KeywordService;
//import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//public class KeywordController {
//
//    private final KeywordService keywordService;
//
//    @PostMapping("/api/keyword/search")
//    public List<PostListResponseDto> search(@RequestBody String keyword){
//        return keywordService.search(keyword);
//    }
//
//
//
//
//}
