package com.soldoc.tech.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.context.Theme;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostAllRequestDto {
    public String title;
    private String body;
    private String author;

    private List<String> keywords;
    private String theme;

}
