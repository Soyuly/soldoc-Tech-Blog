package com.soldoc.tech.domain.keyword.dao;

import com.soldoc.tech.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordDao extends JpaRepository<Keyword, Long> {
}
