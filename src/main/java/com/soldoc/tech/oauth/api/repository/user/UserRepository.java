package com.soldoc.tech.oauth.api.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.soldoc.tech.oauth.api.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    User findByUserId(String id);
}

