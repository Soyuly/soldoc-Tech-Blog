package com.soldoc.tech.oauth.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Getter
/*
 appplication.peroperties의 속성들을 가져오기 위한 클래스
 여기서 정의한 변수는 applicationProperties에서 변수들을 가져온다.
*/
@ConfigurationProperties(prefix = "app") //application.properties에 정의한 app 이름
public class AppProperties {
    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth { // app.Auth.~~~
        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;
    }

    public static final class OAuth2 {  //app.Oauth2.~~
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}

