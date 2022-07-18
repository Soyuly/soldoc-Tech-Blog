package com.soldoc.tech.domain.theme.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.soldoc.tech.domain.keyword.model.Keyword;
import lombok.Builder;
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

    @JsonBackReference
   @OneToMany(mappedBy = "theme")
   private List<Keyword> keywords = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public Theme(String name){
        this.name = name;
    }
}
