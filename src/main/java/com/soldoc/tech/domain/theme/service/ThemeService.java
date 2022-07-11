package com.soldoc.tech.domain.theme.service;

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
    public Long save(ThemeSaveRequestdDto themeSaveRequestdDto){
        return themeDao.save(themeSaveRequestdDto.toEntity()).getId();
    }

    // 해당 주제에 맞는 Theme객체 찾기
    public Theme findByName (String name){
        Theme entity = themeDao.findByName(name);

        return entity;
    }
}
