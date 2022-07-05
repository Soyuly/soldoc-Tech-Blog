package com.soldoc.tech.domain.postkeyword.dao;



import com.soldoc.tech.domain.postkeyword.model.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostkeywordDao extends JpaRepository<PostKeyword, Long> {
}
