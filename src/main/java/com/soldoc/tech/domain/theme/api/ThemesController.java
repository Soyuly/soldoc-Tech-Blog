package com.soldoc.tech.domain.theme.api;

import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.keyword.Service.KeywordService;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.dto.PostKeywordSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.service.PostKeywordService;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import com.soldoc.tech.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThemesController {

    private final PostService postService;
    private final ThemeService themeService;
    private final KeywordService keywordService;
    private final PostKeywordService postKeywordService;

    // Theme Create
    @PostMapping("/api/theme")
    public Long save(@RequestBody ThemeSaveRequestdDto themeSaveRequestdDto){
        return themeService.save(themeSaveRequestdDto);
    }


}
