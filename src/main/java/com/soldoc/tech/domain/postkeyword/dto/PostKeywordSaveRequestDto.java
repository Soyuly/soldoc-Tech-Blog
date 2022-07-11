package com.soldoc.tech.domain.postkeyword.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Builder;

public class PostKeywordSaveRequestDto {
    private Post post;
    private Keyword keyword;

    private String name;

    @Builder
    public PostKeywordSaveRequestDto(Post post, Keyword keyword, String name){
        this.post = post;
        this.keyword = keyword;
        this.name = name;
    }

    public PostKeyword toEntity(){
        return PostKeyword.builder()
                .post(post)
                .keyword(keyword)
                .name(name)
                .build();
    }

}
