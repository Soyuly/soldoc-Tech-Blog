package com.soldoc.tech.domain.theme.service;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.domain.keyword.Service.KeywordService;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.theme.dao.ThemeDao;
import com.soldoc.tech.domain.theme.dto.ThemeKeywordsResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ThemeService {
    private final ThemeDao themeDao;
    private final KeywordService keywordService;
    // CREATE
    @Transactional
    public PostApiResponse<Object> save(ThemeSaveRequestdDto themeSaveRequestdDto){
        Long themeId = themeDao.save(themeSaveRequestdDto.toEntity()).getId();
        return PostApiResponse.success("theme_name",themeSaveRequestdDto.getName());

    }
    @Transactional
    public PostApiResponse<Object> delete(Long themeId){
       Theme theme  = themeDao.findById(themeId).orElseThrow(()-> new IllegalArgumentException("해당 테마가 없습니다"));
       themeDao.delete(theme);
        return PostApiResponse.success("themeId",themeId);

    }

    @Transactional
        public List<ThemeResponseDto> findAllDesc() {
            return themeDao.findAllDesc().stream()
                    .map(ThemeResponseDto::new)
                    .collect(Collectors.toList());
        }

        public List<ThemeKeywordsResponseDto> findByThemeAndKeywords(){
            List<ThemeResponseDto> themeList = themeDao.findAllDesc().stream()
                    .map(ThemeResponseDto::new)
                    .collect(Collectors.toList());

            List<ThemeKeywordsResponseDto> result = new ArrayList<>();
            for(ThemeResponseDto theme : themeList){
                ThemeKeywordsResponseDto themeKeywordsList =  new ThemeKeywordsResponseDto(theme, keywordService.findByThemeId(theme.getId()));
                result.add(themeKeywordsList);
            }

        return result;
        }



    // 해당 주제에 맞는 Theme객체 찾기
    public Theme findByName (String name){
        Theme ThemeEntity = themeDao.findDistinctByName(name);

        return ThemeEntity;
    }
}
