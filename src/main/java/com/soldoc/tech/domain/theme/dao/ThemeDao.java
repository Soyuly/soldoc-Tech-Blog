package com.soldoc.tech.domain.theme.dao;

import com.soldoc.tech.domain.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeDao extends JpaRepository<Theme, Long> {

    Theme findDistinctByName(String name);

    @Query("SELECT t FROM Theme t order by t.id desc ")
    List <Theme> findAllDesc();
}
