package com.soldoc.tech.domain.keyword.api;

import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordDao keywordDao;

    @GetMapping("/api/keywords/{id}")
    public ArrayList<String> getKewordById(@PathVariable("id") Long themeId) {
        ArrayList<String> result = new ArrayList<String>();
        System.out.println(themeId);
        keywordDao.findAllByThemeId(themeId).forEach(keyword -> result.add(keyword.getName()));

        return result;
    }



}
