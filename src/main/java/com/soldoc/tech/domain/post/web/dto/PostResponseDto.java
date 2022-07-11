package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import com.sun.org.apache.xpath.internal.compiler.Keywords;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private List<String> keywords;

    @Builder

    //Null일때 exception 필요
    public PostResponseDto(String title ,List<PostKeyword> keywords) {
        this.title = title;
        List<String> result = new ArrayList<>();
        for(PostKeyword postKeyword : keywords){

            System.out.println(postKeyword.getKeyword().getName());
            result.add(postKeyword.getKeyword().getName());
        }
        this.keywords =  result;
    }



}
