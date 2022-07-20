package com.soldoc.tech.domain.theme.dto;

import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordResponseDto;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Getter;

import java.util.List;

@Getter
public class ThemeKeywordsResponseDto {
    private Long themeId;
    private String name;
    private List<KeywordResponseDto> keywords;

    public ThemeKeywordsResponseDto(ThemeResponseDto entity,List<KeywordResponseDto> keywords){
        this.themeId = entity.getId();
        this.name = entity.getName();
        this.keywords = keywords;
    }
}
