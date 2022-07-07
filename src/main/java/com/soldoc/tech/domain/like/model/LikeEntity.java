package com.soldoc.tech.domain.like.model;

import com.soldoc.tech.domain.post.model.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter

public class LikeEntity {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @Column(nullable = false)
    private String ipAddress;

    // Post에 대한 외래키(N : M)
    // N+1문제를 피하기 위해서
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID")
    private Post post;

    //참고
    public String getIpAddress() {
        return this.getIpAddress();
    }




    @Builder
    public LikeEntity(String ipAddress, Post post){
        this.ipAddress = ipAddress;
        this.post = post;
    }

    public static LikeEntity createLikeEntity(String ipAddress, Post post){
        return LikeEntity.builder()
                .ipAddress(ipAddress)
                .post(post)
                .build();
    }
}
