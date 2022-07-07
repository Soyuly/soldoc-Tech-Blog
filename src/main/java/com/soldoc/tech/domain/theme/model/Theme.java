package com.soldoc.tech.domain.theme.model;

import com.soldoc.tech.domain.keyword.model.Keyword;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Theme {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(length= 50)
    private String themeName;

    @Column(length= 50)
    private String themeCode;

    @Column(length= 50)
    private int themeCount;



    @OneToMany(mappedBy = "theme")
    private List<Keyword> keywords = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private String name;

    @Override
    public String toString() {
        return "CateFilterDTO [cateName=" + themeName + ", cateCode=" + themeCode + ", cateCount=" + themeCount + "]";
    }
}
