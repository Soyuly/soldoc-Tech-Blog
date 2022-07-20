package com.soldoc.tech.domain.postkeyword.Service;


import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.dao.PostkeywordDao;
import com.soldoc.tech.domain.postkeyword.dto.PostKeywordSaveRequestDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import com.soldoc.tech.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostKeywordService {

    private final PostDao postDao;
    private final PostkeywordDao postkeywordDao;
    @Transactional
    public Long createPostKeyword(PostKeywordSaveRequestDto postKeywordSaveRequestDto){
        return postkeywordDao.save(postKeywordSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public  List<Post> getRelatedPost(Long id ){
        List<PostKeyword> postKeywordList =  postkeywordDao.findAllByPostId(id);
        Collections.shuffle(postKeywordList);

        int count = 0;

        List<Post> posts =  new ArrayList<Post>();
        for(PostKeyword postKeyword : postKeywordList){
            List<Post> randomPosts = postDao.findAllByPostKeywords_Name(postKeyword.getName());
            Collections.shuffle(randomPosts);
            posts.addAll(randomPosts);

            if(posts.size() >= 5){
                break;
            }


        }
    return posts;


    }
    @Transactional
    public Theme getKeywordsByThemeId(Long id) {
        try {

            Theme theme = postkeywordDao.findFirstKeywordsByPostId(id).getKeyword().getTheme();
            return theme;
        } catch (NullPointerException error) {
            return new Theme();
        }

    }

}
