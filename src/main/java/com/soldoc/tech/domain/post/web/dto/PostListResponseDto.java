package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private String title;
    private String body;
    private String author;
    private LocalDateTime modifiedDate;

    //::new 사용을 위해
    public PostListResponseDto(Post entity){
        this.title = entity.getTitle();
        this.body = entity.getBody();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }

    @Builder()
    public PostListResponseDto(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

}
