package com.soldoc.tech.domain.post.model;


import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "TINYINT DEFAULT 0")
    private short likeCount;

    //작성자
    @Column(nullable = false)
    private String author;


    // N : M 외래키
    // postKeywords : Post안에 여러개의 키워드가 있다는 것을 알려주는 변수
    @OneToMany(mappedBy = "post")
    private List<PostKeyword> postKeywords = new ArrayList<>();


    @Builder
    public Post(
            String title,
            String body,
            String author,
            int viewCount
    ){
        this.title = title;
        this.body = body;
        this.author = author;
        this.viewCount = viewCount;
    }

    public void update(String title, String body){
        this.title = title;
        this.body = body;
    }



    public static Post createPost(String title, String body, String author){
        return Post.builder()
                .title(title)
                .body(body)
                .author(author)
                .build();
    }


}
