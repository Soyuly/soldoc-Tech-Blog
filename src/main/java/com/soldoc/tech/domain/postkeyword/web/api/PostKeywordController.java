package com.soldoc.tech.domain.postkeyword.web.api;


import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.service.PostKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostKeywordController {

    public final PostKeywordService postKeywordService;

//    @PostMapping("/api/postKeyword/")
//    public void createPostKeyword(PostSaveRequestDto postSaveRequestDto, KeywordSearchReqDto keywordSearchReqDto){
//        postKeywordService.createPostKeyword(postSaveRequestDto, keywordSearchReqDto);
//    }


}
