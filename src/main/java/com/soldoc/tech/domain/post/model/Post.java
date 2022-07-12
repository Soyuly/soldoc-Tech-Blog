package com.soldoc.tech.domain.post.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import com.soldoc.tech.oauth.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@DynamicInsert
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

    @Column(columnDefinition = "varchar(1) default 'N'")
    private String deleteStatus;

    @Column(name="DELETE_DATE", nullable = true)
    private LocalDateTime deleteTime;

    //작성 계정
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    // N : M 외래키
    // postKeywords : Post안에 여러개의 키워드가 있다는 것을 알려주는 변수
    @JsonManagedReference
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostKeyword> postKeywords = new ArrayList<>();


    @Builder
    public Post(
            String title,
            String body,
            String author,
            int viewCount,
            short likeCount,
            User user
    ){
        this.title = title;
        this.body = body;
        this.author = author;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.user = user;
    }

    public void update(String title, String body){
        this.title = title;
        this.body = body;
    }



    public short addLike(){
        this.likeCount = (short) (this.likeCount +1);
        return this.likeCount;

    }
    public short deleteLike(){
        this.likeCount = (short) (this.likeCount -1);
        return this.likeCount;
    }

    public short getLikeCount() { return this.likeCount; }

    public void addViewCount() {
        this.viewCount = this.viewCount+1;
    }

    public void deleteSetStatus() {
        this.deleteStatus = String.valueOf('Y');
    }

    public void setDeleteTime() {
        this.deleteTime = LocalDateTime.now();
    }
    public void restoreDeleteTime() {
        this.deleteTime = null;
    }

    public void restoreStatus() {
        this.deleteStatus = String.valueOf('N');
    }

//    public LocalDateTime getModifiedDate() { return this.getModifiedDate(); }

    public static Post createPost(String title, String body, String author, User user){
        return Post.builder()
                .title(title)
                .body(body)
                .author(author)
                .user(user)
                .build();
    }


}
