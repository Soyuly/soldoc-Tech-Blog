package com.soldoc.tech.domain.user.repository;

import com.soldoc.tech.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long>{

    // 이메일을 통해 이미 생성된 사용자인지 확인
    Optional<User> findByEmail(String email);

}
