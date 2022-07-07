package com.soldoc.tech.oauth.security.info;

import java.util.Map;

/*
* 나중에 다른(네이버, 카카오)로그인을 구현해야할 수 있으니
* 추상클래스 만들어서 필요한것만 구현하면 됨.
* */
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}

