package com.soldoc.tech.domain.keyword.web.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class KeywordSearchReqDto {

    private String name;


    @Builder
    public KeywordSearchReqDto(String name){
        this.name = name;
    }
    public Keyword toEntity(){
        return Keyword.builder()
                .name(name)
                .build();
    }


//    @Builder
//    public KeywordSearchReqDto(List<String> name){
//        this.name = name;
//    }
//    public Keyword toEntity(){
//        return Keyword.builder()
//                .name(String.valueOf(name))
//                .build();
//    }


}
