package com.soldoc.tech.domain.postkeyword.Service;


import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.dao.PostkeywordDao;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostKeywordService {

    private final PostkeywordDao postkeywordDao;
    @Transactional
    public void createPostKeyword(PostSaveRequestDto postSaveRequestDto, KeywordSearchReqDto keywordSearchReqDto){
        PostKeyword postKeyword = new PostKeyword(postSaveRequestDto.toEntity(), keywordSearchReqDto.toEntity());
        postkeywordDao.save(postKeyword);
    }

}
