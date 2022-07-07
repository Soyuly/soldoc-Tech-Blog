package com.soldoc.tech.domain.like.service;


import com.soldoc.tech.domain.like.dao.LikeDao;
import com.soldoc.tech.domain.like.model.LikeEntity;
import com.soldoc.tech.domain.like.web.dto.LikeDto;
import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeDao likeDao;
    private final PostDao postDao;

    private final EntityManager em;
    private List p;

    @Transactional
    public String like(LikeDto likeDto) {
        return likeDao.save(likeDto.toEntity()).getIpAddress(); //저장되고, 그 중 ip주소 전달
    }


}
