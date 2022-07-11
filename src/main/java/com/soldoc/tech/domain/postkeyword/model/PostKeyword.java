package com.soldoc.tech.domain.postkeyword.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@Table(name="PostKeyword")
public class PostKeyword implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Post테이블의 외래키
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "POST_ID")
    Post post;

    // Keyword테이블의 외래키
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "KEYWORD_ID")
    Keyword keyword;

    @Builder
    public PostKeyword(Post post, Keyword keyword){
        this.post = post;
        this.keyword = keyword;
    }
}
