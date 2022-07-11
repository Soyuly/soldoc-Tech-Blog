package com.soldoc.tech.domain.theme.dto;

import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Getter;

@Getter
public class ThemeResponseDto {
    private Long id;
    private String name;

    public ThemeResponseDto(Theme entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
