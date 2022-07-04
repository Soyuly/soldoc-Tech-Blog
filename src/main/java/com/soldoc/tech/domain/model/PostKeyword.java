package com.soldoc.tech.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
@Entity
public class PostKeyword {

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
