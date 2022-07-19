package com.soldoc.tech.domain.keyword.api;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.domain.keyword.Service.KeywordService;
import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordResponseDto;
import com.soldoc.tech.domain.keyword.web.dto.KeywordVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordDao keywordDao;
    private final KeywordService keywordService;
    @GetMapping("/api/keywords/{id}")
    public List<KeywordResponseDto> getKewordById(@PathVariable("id") Long themeId) {
        ArrayList<String> result = new ArrayList<String>();


        return keywordService.findByThemeId(themeId);
    }

    @PostMapping("/api/v1/keyword")
    public PostApiResponse<Object> save(@RequestBody KeywordVO keywordVO){
        return keywordService.save(keywordVO);
    }


}
