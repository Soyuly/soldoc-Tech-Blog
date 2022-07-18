package com.soldoc.tech.domain.keyword.Service;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;

import com.soldoc.tech.domain.keyword.web.dto.KeywordResponseDto;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import com.soldoc.tech.domain.keyword.web.dto.KeywordVO;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostResponseDto;
import com.soldoc.tech.domain.theme.dao.ThemeDao;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordDao keywordDao;
    private final ThemeDao themeDao;


    public List<Keyword> showKeyword(){
        return keywordDao.findAll();
    }

    @Transactional
    public Keyword findById(Long id){
        Keyword keyword = keywordDao.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시물이 존재하지 않습니다 id = " + id));
        return keyword;
    }

    @Transactional
    public PostApiResponse<Object> save(KeywordVO keywordVO){


        Theme themeEntity = themeDao.findDistinctByName(keywordVO.getTheme());

        Keyword keyword = keywordDao.save(KeywordSaveRequestDto.builder()
                .name(keywordVO.getName())
                .theme(themeEntity)
                .build().toEntity());

        return PostApiResponse.created("keyword",keyword);
    }


}
