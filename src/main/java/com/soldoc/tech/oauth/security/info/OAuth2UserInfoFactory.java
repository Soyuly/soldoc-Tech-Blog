package com.soldoc.tech.oauth.security.info;

import com.soldoc.tech.oauth.security.entity.ProviderType;

import java.util.Map;

/*
* 로그인한 곳이 카카오인지 구글인지 네이버인지등을 선택해서 각각에 맞게
* 정보를 가져오게 함
* */
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
        switch (providerType) {
            case GOOGLE: return new GoogleOAuth2UserInfo(attributes);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }
}

