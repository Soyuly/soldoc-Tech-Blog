package com.soldoc.tech.oauth.api.controlller.auth;

import com.soldoc.tech.common.PostApiResponse;
import com.soldoc.tech.oauth.api.dto.TokenResponseDto;
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
import jdk.nashorn.internal.parser.Token;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public PostApiResponse<TokenResponseDto> test(HttpServletRequest request, HttpServletResponse response){

        String token = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(token);
        Claims claims = authToken.getTokenClaims();
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(claims.getSubject());
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setAccessToken(token);
        tokenResponseDto.setRefreshToken(userRefreshToken.getRefreshToken());
        System.out.println(tokenResponseDto.getRefreshToken());
        return PostApiResponse.success("token", tokenResponseDto);
    }
    @PostMapping("/login")
    // httpservlet Reuest??? ???????????? ?????? ????????? ??? ??????.
    public ApiResponse login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody AuthReqModel authReqModel
    ) {

        // ????????? ???????????? ????????? ????????????.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReqModel.getId(),
                        authReqModel.getPassword()
                )
        );

        // ?????? ????????? AuthReqModel?????? ???????????? ????????????.
        String userId = authReqModel.getId();

        // SecurityCOntextHolder??? ?????? ?????????????????? ?????? ????????? ?????????  ??????
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Date now = new Date();

        // access token??? ?????????.
        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

        // Refresh Token??? ????????????.
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token ?????? DB ??????
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserId(userId);
        if (userRefreshToken == null) {
            // ?????? ?????? ?????? ??????
            userRefreshToken = new UserRefreshToken(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(userRefreshToken);
        } else {
            // DB??? refresh ?????? ????????????
            userRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        int cookieMaxAge = (int) refreshTokenExpiry / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);

        return ApiResponse.success("token", accessToken.getToken());
    }

    /*
    * ?????? ??????
    * */
    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {

        // access token ??????
        String accessToken = HeaderUtil.getAccessToken(request);
        AuthToken authToken = tokenProvider.convertAuthToken(accessToken);

        // ?????? ???????????? ???????????? ??????, ???????????? ????????? ????????? ?????? ?????? Return
        if (!authToken.validate()) {
            return ApiResponse.invalidAccessToken();
        }

        // expired access token ?????? ??????
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

        // refresh token??? ???????????? ??????.
        if (authRefreshToken.validate()) {
            return ApiResponse.invalidRefreshToken();
        }

        // userId refresh token ?????? DB ??????
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken);
        if (userRefreshToken == null) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();

        // ????????? Access Token ??????
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // refresh ?????? ????????? 3??? ????????? ?????? ??????, refresh ?????? ??????
        if (validTime <= THREE_DAYS_MSEC) {
            // refresh ?????? ??????
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );

            // DB??? refresh ?????? ????????????
            userRefreshToken.setRefreshToken(authRefreshToken.getToken());

            int cookieMaxAge = (int) refreshTokenExpiry / 60;
            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
            CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
        }

        return ApiResponse.success("token", newAccessToken.getToken());
    }
}

