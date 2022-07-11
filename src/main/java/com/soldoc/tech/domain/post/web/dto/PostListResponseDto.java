package com.soldoc.tech.domain.post.web.dto;

import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostListResponseDto {
    private String title;
    private String body;
    private String author;

    private short likeCount;

    private int viewCount;

    private List<PostKeyword> postKeywords;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    //::new 사용을 위해
    public PostListResponseDto(Post entity){
        this.title = entity.getTitle();
        this.postKeywords = entity.getPostKeywords();
        this.body = entity.getBody();
        this.author = entity.getAuthor();
        this.likeCount = entity.getLikeCount();
        this.viewCount = entity.getViewCount();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
    }

    @Builder()
    public PostListResponseDto(String title, String body, String author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

}
