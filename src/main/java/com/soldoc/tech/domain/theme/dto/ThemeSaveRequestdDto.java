package com.soldoc.tech.domain.theme.dto;

import com.soldoc.tech.domain.theme.model.Theme;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThemeSaveRequestdDto {
    private String name;

    @Builder
    public ThemeSaveRequestdDto(String name){
        this.name = name;
    }

    public Theme toEntity(){
        return Theme.builder()
                .name(name)
                .build();
    }
}
