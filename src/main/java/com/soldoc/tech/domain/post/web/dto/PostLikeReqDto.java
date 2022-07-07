package com.soldoc.tech.domain.post.web.dto;


import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostLikeReqDto {
    private final short likeCount;

    @Builder
    public PostLikeReqDto(short likeCount){
        this.likeCount =  likeCount;
    }

    public Post toEntity(){
        return Post.builder()
                .likeCount(likeCount)
                .build();
    }
}
