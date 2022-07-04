package com.soldoc.tech.domain.postkeyword.model;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@Entity
@IdClass(PostKeywordId.class)
@Table(name="PostKeyword")
public class PostKeyword implements Serializable {

    // Post테이블의 외래키
    @Id
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    Post post;

    // Keyword테이블의 외래키
    @Id
    @ManyToOne
    @JoinColumn(name = "KEYWORD_ID")
    Keyword keyword;
}
