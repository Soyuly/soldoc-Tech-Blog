package com.soldoc.tech.oauth.security.service;

import com.soldoc.tech.oauth.api.repository.user.UserRepository;
import com.soldoc.tech.oauth.security.entity.ProviderType;
import com.soldoc.tech.oauth.security.entity.RoleType;
import com.soldoc.tech.oauth.security.entity.UserPrincipal;
import com.soldoc.tech.oauth.security.exception.OAuthProviderMissMatchException;
import com.soldoc.tech.oauth.security.info.OAuth2UserInfo;
import com.soldoc.tech.oauth.security.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import com.soldoc.tech.oauth.api.entity.user.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
// DefaultOAuth2UserService를 상속 => loaduser()를 구현하는게 핵심
// 공급자로부터 access toeken을 받고 호
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User user = super.loadUser(userRequest);

        // atrribute를 찍어보면 다른 이름으로 데이터가 들어오는 것을 확인할 수 있다.
        System.out.println("atrributes : " + user.getAttributes());

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    //인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이 업데이트를 진행
    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());

        User savedUser;

        if(userOptional.isPresent()){
            savedUser = userOptional.get();
            savedUser = updateUser(savedUser, userInfo);
        }
        else{
            savedUser = createUser(userInfo, ProviderType.GOOGLE);
        }

        return UserPrincipal.create(savedUser, userInfo.getAttributes());
    }

    // 유저를 새로 생성한다. 사실 providerType은 의미가 없는 매개변수라서
    // 나중에 리펙토링 할 때 수정예정
    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(
                userInfo.getId(),
                userInfo.getName(),
                userInfo.getEmail(),
                "Y",
                userInfo.getImageUrl(),
                providerType,
                RoleType.USER,
                now,
                now
        );

        return userRepository.saveAndFlush(user);
    }

    // 이미 유저가 존재하면 이름과 사진을 업데이트 한다.
    private User updateUser(User user, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !user.getUsername().equals(userInfo.getName())) {
            user.setUsername(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !user.getProfileImageUrl().equals(userInfo.getImageUrl())) {
            user.setProfileImageUrl(userInfo.getImageUrl());
        }

        return user;
    }
}
