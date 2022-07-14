package com.soldoc.tech.domain.theme.service;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.theme.dao.ThemeDao;
import com.soldoc.tech.domain.theme.dto.ThemeResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ThemeService {
    private final ThemeDao themeDao;

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


    // 해당 주제에 맞는 Theme객체 찾기
    public Theme findByName (String name){
        Theme ThemeEntity = themeDao.findDistinctByName(name);

        return ThemeEntity;
    }
}
