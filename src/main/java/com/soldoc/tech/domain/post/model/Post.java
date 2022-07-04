package com.soldoc.tech.domain.post.model;

import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    // 기본키(post_id)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // N : M 외래키
    // postKeywords : Post안에 여러개의 키워드가 있다는 것을 알려주는 변수
    @OneToMany(mappedBy = "post")
    private List<PostKeyword> postKeywords = new ArrayList<>();

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

    // 작성 날짜를 자동으로 생성해주는 어노테이션
    @CreatedDate
    @Column(name="CREATE_DATE", updatable = false, nullable = false)
    private LocalDateTime createDate;

    // 최근 수정 날짜를 자동으로 반영해준다.
    @LastModifiedDate
    @Column(name="UPDATE_DATA", nullable = false)
    private LocalDateTime updateDate;










}
