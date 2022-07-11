package com.soldoc.tech.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PostVO {
    public String title;
    private String body;
    private String author;

    private List<String> keywords;
    private String theme;

}
