package com.soldoc.tech.domain.like.dao;

import com.soldoc.tech.domain.like.model.LikeEntity;
import com.soldoc.tech.domain.like.web.dto.LikeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LikeDao extends JpaRepository<LikeEntity, Long> {
}
