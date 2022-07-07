package com.soldoc.tech.domain.keyword.model;

import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Keyword {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 키워드이름
    @Column(length = 25, nullable = false)
    private String name;

    // Theme에 대한 외래키(N : 1)
    @ManyToOne
    @JoinColumn(name="THEME_ID")
    private Theme theme;

    // Post에 대한 외래키(N : M)
    @OneToMany(mappedBy = "keyword")
    private List<PostKeyword> postKeywords = new ArrayList<>();

    @Builder
    public Keyword(String name, Theme theme){
        this.name = name;
        this.theme = theme;
    }



}
