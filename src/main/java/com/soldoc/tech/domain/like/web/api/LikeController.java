package com.soldoc.tech.domain.like.web.api;


import com.soldoc.tech.domain.like.model.LikeEntity;

import com.soldoc.tech.domain.like.service.LikeService;
import com.soldoc.tech.domain.like.web.dto.LikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;

@Getter
@RequiredArgsConstructor
@RequestMapping("/api/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public String like(@RequestBody LikeDto likeDto){
        return likeService.like(likeDto);
    }


}
