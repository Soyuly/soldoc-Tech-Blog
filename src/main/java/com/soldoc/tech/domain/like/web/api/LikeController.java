package com.soldoc.tech.domain.like.web.api;

import com.soldoc.tech.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;


//    //TODO 인증키 정보 필요 : UserAdapter
//    @PostMapping("/like/{recipeId}")
//    public ResponseEntity<String> addLike(
//            @AuthenticationPrincipal
//            @PathVariable Long recipeId) {
//
//        boolean result = false;
//
//        return result ?
//                new ResponseEntity<>(HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
}