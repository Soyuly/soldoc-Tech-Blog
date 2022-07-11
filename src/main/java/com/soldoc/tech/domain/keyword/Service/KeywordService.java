package com.soldoc.tech.domain.keyword.Service;

import com.soldoc.tech.domain.keyword.dao.KeywordDao;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KeywordService {
    private final KeywordDao keywordDao;

    public List<Keyword> showKeyword(){
        return keywordDao.findAll();
    }

    @Transactional
    public void save(KeywordSearchReqDto keywordSearchReqDto){
        keywordDao.save(keywordSearchReqDto.toEntity());
    }
}
