package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.post.model.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostListResponseDto(Post entity){
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
