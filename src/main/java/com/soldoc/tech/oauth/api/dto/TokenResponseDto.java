package com.soldoc.tech.oauth.api.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;

    @Size(max = 256)
    private String refreshToken;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
