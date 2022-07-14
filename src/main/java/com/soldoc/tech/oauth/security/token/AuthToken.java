package com.soldoc.tech.oauth.security.token;


import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    AuthToken(String id, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    private String createAuthToken(String id, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS256,key)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(SignatureAlgorithm.HS256,key)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("유효하지 않은 JWT 토큰 검증방식입니다.");
        } catch (MalformedJwtException e) {
            log.info("유효하지 않은 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다. /refresh를 통해 갱신해주세요!");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT토큰 형식입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰의 압축 방식이 잘못 되었습니다.");
        } catch (io.jsonwebtoken.security.SignatureException e){
            log.info("검증되지 않은 토큰 입니다.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}

