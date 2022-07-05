package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private String title;

    @Builder
    public PostResponseDto(String title) {
        this.title = title;
    }


}
