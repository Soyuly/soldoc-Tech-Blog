package com.soldoc.tech.domain.banner.dao;

import com.soldoc.tech.domain.banner.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerDao extends JpaRepository<Banner, Long> {
}
