package com.soldoc.tech.domain.post.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateRequestDto {

    private String title;
    private String body;

    //DTO 생성자 생성
    @Builder
    public PostUpdateRequestDto(String title, String body) {
        this.title = title;
        this.body = body;
    }

}
