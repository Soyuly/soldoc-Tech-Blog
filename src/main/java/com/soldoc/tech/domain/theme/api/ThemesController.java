package com.soldoc.tech.domain.theme.api;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.keyword.Service.KeywordService;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.Service.PostKeywordService;
import com.soldoc.tech.domain.postkeyword.dto.PostKeywordSaveRequestDto;
import com.soldoc.tech.domain.theme.dto.ThemeKeywordsResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import com.soldoc.tech.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemesController {

    private final PostService postService;
    private final ThemeService themeService;
    private final KeywordService keywordService;
    private final PostKeywordService postKeywordService;

    @GetMapping("/api/v1/theme")
    public List<ThemeResponseDto> findAllDesc(){
        return themeService.findAllDesc();
    }
    // Theme Create
    @PostMapping("/api/v1/theme")
    public PostApiResponse<Object> save(@RequestBody ThemeSaveRequestdDto themeSaveRequestdDto){
        return themeService.save(themeSaveRequestdDto);
    }

    @DeleteMapping("/api/v1/theme/{id}")
    public PostApiResponse<Object> delete(@PathVariable Long id){
        return themeService.delete(id);
    }

    @GetMapping("/api/v1/themekeywords")
    public List<ThemeKeywordsResponseDto> getThemeKeywords(){
        return themeService.findByThemeAndKeywords();
    }


}
