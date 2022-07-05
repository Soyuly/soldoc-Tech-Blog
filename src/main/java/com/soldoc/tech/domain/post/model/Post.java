package com.soldoc.tech.domain.post.model;

import com.soldoc.tech.domain.like.model.Like;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Post extends BaseTime {

    // 기본키(post_id)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    //게시글 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    //조회수
    @Column(columnDefinition = "INT DEFAULT 0")
    private int viewCount;

    //작성자
    private String author;

    //좋아요 개수
    @Column(name="LIKE_COUNT", columnDefinition = "TINYINT DEFAULT 0")
    private short likeCount;

    // N : M 외래키
    // postKeywords : Post안에 여러개의 키워드가 있다는 것을 알려주는 변수
    @OneToMany(mappedBy = "post")
    private List<PostKeyword> postKeywords = new ArrayList<>();

    // Like에 대한 외래키(N : 1)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<Like> likes = new HashSet<>();

    @Builder
    public Post(
            String title,
            String body,
            String author,
            int viewCount,
            short likeCount
    ){
        this.title = title;
        this.body = body;
        this.author = author;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }



    public void update(String title, String body){
        this.title = title;
        this.body = body;
    }

}
