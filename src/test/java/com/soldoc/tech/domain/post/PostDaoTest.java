package com.soldoc.tech.domain.post;


import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostDaoTest {

    @Autowired
    PostDao postDao;

    @After
    public void cleanup() {
        postDao.deleteAll();
    }


    @Test
    public void 게시물저장_불러오기() {
        //given
        String title = "테스트 게시물 제목";
        String body = "테스트 게시물 내용";
        Post a =
        postDao.save(Post.builder()
                .title(title)
                .body(body)
                .build());
        //when
        List<Post> postList = postDao.findAll();

        //then
        Post post = postList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getBody()).isEqualTo(body);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2022,4,6,0,0,0);
        postDao.save(Post.builder()
                .title("title")
                .body("body")
                .author("author")
                .viewCount(4)
                .build());

        //when
        List<Post> postList = postDao.findAll();

        //then
        Post post = postList.get(0);

        System.out.println("시간: createDate=" + post.getCreatedDate() + "updateDate=" + post.getModifiedDate());
//        log.debug(">>>>> createDate=" + post.getCreatedDate() + "updateDate=" + post.getModifiedDate());
        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);

    }




}
