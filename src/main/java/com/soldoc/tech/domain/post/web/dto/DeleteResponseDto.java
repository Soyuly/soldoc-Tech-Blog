package com.soldoc.tech.domain.post.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DeleteResponseDto {

    private String message;
    private String status;

    @Builder
    public DeleteResponseDto(String message, String status){
        this.message = message;
        this.status = status;
    }

}
