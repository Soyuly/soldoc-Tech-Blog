package com.soldoc.tech.domain.theme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Theme {
    // 기본키
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @OneToMany(mappedBy = "theme")
//    private List<Keyword> keywords = new ArrayList<>();

    @Column(length = 50, nullable = false)
    private String name;

    @Builder
    public Theme(String name){
        this.name = name;
    }
}
