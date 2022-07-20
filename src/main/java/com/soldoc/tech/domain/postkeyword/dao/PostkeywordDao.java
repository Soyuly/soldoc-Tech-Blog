package com.soldoc.tech.domain.postkeyword.dao;



import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostkeywordDao extends JpaRepository<PostKeyword, Long> {

    @Query(value="SELECT pk FROM PostKeyword pk WHERE pk.keyword.name = ?1")
    Page<PostKeyword> findByKeywordContaining(String word, PageRequest pageRequest);

    List<PostKeyword> findAllByPostId(long id);

    PostKeyword findKeywordByPostId(long id);
}
