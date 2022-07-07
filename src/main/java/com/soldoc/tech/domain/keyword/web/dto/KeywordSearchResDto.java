package com.soldoc.tech.domain.keyword.web.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import lombok.Builder;
import lombok.Getter;


@Getter
public class KeywordSearchResDto {

    private String name;

    //::new 사용을 위해
    public KeywordSearchResDto(Keyword entity){
        this.name = entity.getName();
    }


}
