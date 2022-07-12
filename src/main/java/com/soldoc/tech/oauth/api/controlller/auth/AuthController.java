package com.soldoc.tech.oauth.api.controlller.auth;

import com.soldoc.tech.oauth.api.entity.auth.AuthReqModel;
import com.soldoc.tech.oauth.api.entity.user.UserRefreshToken;
import com.soldoc.tech.oauth.api.repository.user.UserRefreshTokenRepository;
import com.soldoc.tech.oauth.common.ApiResponse;
import com.soldoc.tech.oauth.config.properties.AppProperties;
import com.soldoc.tech.oauth.security.entity.RoleType;
import com.soldoc.tech.oauth.security.entity.UserPrincipal;
import com.soldoc.tech.oauth.security.token.AuthToken;
import com.soldoc.tech.oauth.security.token.AuthTokenProvider;
import com.soldoc.tech.oauth.utils.CookieUtil;
import com.soldoc.tech.oauth.utils.HeaderUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @GetMapping("/token")
    public String test(HttpServletRequest request, HttpServletResponse response){

        String token = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(token);
        Claims claims = authToken.getTokenClaims();
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(claims.getSubject());
        return "{\"access_token \" : " + token + ",\n\"refresh_token \" : " + userRefreshToken.getRefreshToken() + "}";
    }
    @PostMapping("/login")
    // httpservlet Reuest를 사용하면 값을 받아올 수 있다.
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReqModel authReqModel
    ) {

        // 토큰을 생성하여 인증을 시도한다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );

        // 내가 생성한 AuthReqModel에서 아이디를 가져온다.
        String userId = authReqModel.getId();

        // SecurityCOntextHolder는 누가 인증했는지에 대한 정보를 가지고  있다
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();

        // access token을 만든다.
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        // Refresh Token을 생성한다.
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // 없는 경우 새로 등록
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    /*
    * 토큰 갱신
    * */
    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {

        // access token 확인
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);

        // 정상 토큰인지 확인하는 과정, 정상토큰 아니면 잘못된 토큰 응답 Return
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token 인지 확인
        Claims claims = authToken.getExpiredTokenClaims();
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse((null));
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        // refresh token이 정상인지 확인.
        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token 으로 DB 확인
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();

        // 새로운 Access Token 생성
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB에 refresh 토큰 업데이트
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }
}

