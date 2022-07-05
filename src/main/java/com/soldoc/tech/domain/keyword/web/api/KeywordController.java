package com.soldoc.tech.domain.keyword.web.api;


import com.soldoc.tech.domain.keyword.service.KeywordService;
import com.soldoc.tech.domain.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class KeywordController {

    private final KeywordService keywordService;



}
