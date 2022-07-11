package com.soldoc.tech.domain.keyword.Service;

import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordResponseDto;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordDao keywordDao;

    @Transactional
    public Long save(KeywordSaveRequestDto keywordSaveRequestDto){
        return keywordDao.save(keywordSaveRequestDto.toEntity()).getId();
    }


}
