package com.soldoc.tech.domain.keyword.web.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Getter;

@Getter
public class KeywordResponseDto {
    private Long id;
    private String name;

    public KeywordResponseDto(Keyword entity){
        this.id = entity.getId();;
        this.name = entity.getName();
    }
}
