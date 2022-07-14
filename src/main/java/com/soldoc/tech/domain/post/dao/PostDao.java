package com.soldoc.tech.domain.post.dao;

import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {


    @Query("SELECT p FROM Post p WHERE p.deleteStatus <> 'Y' ORDER BY p.id DESC")
    Page<Post> findAll(PageRequest pageRequest);

    List<Post> findAllByUserId(Long userId);

    Page<PostListResponseDto> findByTitleContaining(String word, PageRequest pageRequest);
    Page<PostListResponseDto> findByBodyContaining(String word, PageRequest pageRequest);

    List<Post> findTop3ByLikeCountGreaterThanOrderByLikeCountDesc(short likeCount);

//AndLikeCountOrderByLikeCountDesc

    @Query(value="SELECT pk.keyword.name FROM PostKeyword pk")
    Page<PostKeyword> findByKeywordContaining(String word, PageRequest pageRequest);

    //해당하는 pk.keyword.name의 name을 가진 post내용을 불러오도록
    @Query(value="SELECT p FROM PostKeyword pk INNER JOIN Post p on p.id = pk.post.id WHERE pk.keyword.name = ?1")
    Page<Post> findByPostKeywords(String word, PageRequest pageRequest);

    @Query(value="SELECT count (p) > 0 FROM PostKeyword pk INNER JOIN Post p on p.id = pk.post.id WHERE pk.name = :word")
    boolean keywordExists(@Param("word") String word, PageRequest pageRequest);


}
