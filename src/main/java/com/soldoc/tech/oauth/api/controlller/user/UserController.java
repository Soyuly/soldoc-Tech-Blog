package com.soldoc.tech.oauth.api.controlller.user;

import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.oauth.api.service.UserService;
import com.soldoc.tech.oauth.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import com.soldoc.tech.oauth.api.entity.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostDao postDao;
    @GetMapping
    public ApiResponse getUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

        return ApiResponse.success("user", user);
    }

    @GetMapping("/posts")
    public List<Post> getPostByUserId(){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getUser(principal.getUsername());

    return postDao.findAllByUserId(user.getId());

    }
}

