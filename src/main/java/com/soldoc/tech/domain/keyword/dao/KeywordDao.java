package com.soldoc.tech.domain.keyword.dao;

import com.soldoc.tech.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordDao extends JpaRepository<Keyword, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<Keyword> findAllSearch(String keyword);


    List<Keyword> findByThemeId(Long themeId);

}
