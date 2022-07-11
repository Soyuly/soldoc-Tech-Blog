package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    private String title;
    private String body;
    private String author;



    @Builder
    public PostSaveRequestDto(
            String title,
            String body,
            String author
            ){
        this.title = title;
        this.body = body;
        this.author = author;
    }

    // 절대로, Entity클래스를 Request / Response 클래스로 사용해서는 안된다.
    // Entity클래스는 데이터베이스와 가장 밀접한 핵심 클래스 이기 때문
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .body(body)
                .author(author)
                .build();
    }





}
