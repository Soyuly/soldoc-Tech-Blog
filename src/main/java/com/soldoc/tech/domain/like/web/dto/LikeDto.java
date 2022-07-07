package com.soldoc.tech.domain.like.web.dto;


import com.soldoc.tech.domain.like.model.LikeEntity;
import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LikeDto {
    private String ipAddress;


    @Builder
    public LikeDto(String ipAddress){
        this.ipAddress = ipAddress;
    }


    public LikeEntity toEntity() {
        return LikeEntity.builder()
                .ipAddress(ipAddress)
                .build();
    }



}
