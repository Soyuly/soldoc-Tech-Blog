package com.soldoc.tech.domain.keyword.web.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Builder;

public class KeywordSaveRequestDto {
    private String name;
    private Theme theme;

    @Builder
    public KeywordSaveRequestDto(String name, Theme theme){
        this.name = name;
        this.theme = theme;
    }

    public Keyword toEntity(){
        return Keyword.builder()
                .name(name)
                .theme(theme)
                .build();
    }

}
