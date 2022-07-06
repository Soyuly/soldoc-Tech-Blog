package com.soldoc.tech.oauth.api.service;

import com.soldoc.tech.oauth.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import com.soldoc.tech.oauth.api.entity.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}

