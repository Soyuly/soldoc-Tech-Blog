package com.soldoc.tech.domain.postkeyword.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Builder;

public class PostKeywordSaveRequestDto {
    private Post post;
    private Keyword keyword;

    @Builder
    public PostKeywordSaveRequestDto(Post post, Keyword keyword){
        this.post = post;
        this.keyword = keyword;
    }

    public PostKeyword toEntity(){
        return PostKeyword.builder()
                .post(post)
                .keyword(keyword)
                .build();
    }

}
