package com.soldoc.tech.domain.postkeyword.service;


import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.dao.PostkeywordDao;
import com.soldoc.tech.domain.postkeyword.dto.PostKeywordSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostKeywordService {

    private final PostkeywordDao postkeywordDao;
    @Transactional
    public Long createPostKeyword(PostKeywordSaveRequestDto postKeywordSaveRequestDto){
        return postkeywordDao.save(postKeywordSaveRequestDto.toEntity()).getId();
    }

}
