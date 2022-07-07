package com.soldoc.tech.oauth.utils;

import javax.servlet.http.HttpServletRequest;

/*
* Header에서 인증정보만 Parsing하는 클래스
* */
public class HeaderUtil {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    // 헤더에서 Authorization 적힌 부분을 찾고, 해당 글자 이후로 전부 잘라서 return
    public static String getAccessToken(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_AUTHORIZATION);

        if (headerValue == null) {
            return null;
        }

        if (headerValue.startsWith(TOKEN_PREFIX)) {
            return headerValue.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}

