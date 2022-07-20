package com.soldoc.tech.domain.postkeyword.web.api;


import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.Service.PostKeywordService;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import java.util.Random;

import com.soldoc.tech.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostKeywordController {

    public final PostKeywordService postKeywordService;

    @GetMapping("/api/v1/relatedposts/{id}")
    public List<Post> getRelatedPosts(@PathVariable Long id){
       return postKeywordService.getRelatedPost(id);
    }

//    @PostMapping("/api/postKeyword/")
//    public void createPostKeyword(PostSaveRequestDto postSaveRequestDto, KeywordSearchReqDto keywordSearchReqDto){
//        postKeywordService.createPostKeyword(postSaveRequestDto, keywordSearchReqDto);
//    }
    @GetMapping("/api/v1/theme/post/{id}")
    public Theme getKeywordByPostId(@PathVariable Long id){
        return postKeywordService.getKeywordsByThemeId(id);
    }

}
