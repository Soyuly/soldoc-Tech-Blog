package com.soldoc.tech.oauth.security.filter;

import com.soldoc.tech.oauth.security.token.AuthToken;
import com.soldoc.tech.oauth.security.token.AuthTokenProvider;
import com.soldoc.tech.oauth.utils.HeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
/*
* 클라이언트가 요청한 JWT TOKEN을 읽어 인증(verify) 하고, 토큰이 유효하면
* SecurityContext 생성
* */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final AuthTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String tokenStr = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(tokenStr);

        /*
        * 사용자 인증정보(아이디, 패스워드)를 Authentiacation으로 감싼 다시 SecurityContext로 감싸고
        * 다시 SecurityContextHolder로 감싸는데, 이 알맹이를 토큰으로 넣어서 바꿔줌.
        * */
        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

