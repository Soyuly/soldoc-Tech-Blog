package com.soldoc.tech.oauth.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soldoc.tech.oauth.api.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
}

