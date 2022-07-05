package com.soldoc.tech.domain.post;


import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.post.web.dto.PostUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @Autowired
    private PostDao postDao;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @After
    public void cleanUp() throws Exception {
        postDao.deleteAll();
    }


    @Test
    public void Post_등록된다() throws Exception {
        //given
        String title = "테스트 게시물 제목";
        String body = "테스트 게시물 내용";
        String author = "테스트 게시물 작성자";
        int viewCount = 100;
        short likeCount = 5;

        //기본적으로 DTO에 내용을 담아서 테스트.
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .body(body)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/contents";

        //해당 url로 데이터 전송 (ResponseEntity 형태로)
        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postDao.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void Post_수정된다() {
        //given
        Post postedData = postDao.save(Post.builder()
                .title("테스트 게시물 제목")
                .body("테스트 게시물 내용")
                .build());
        Long updatedId = postedData.getId();

        String expectedTitle = "테스트 게시물 제목";
        String expectedBody = "테스트 게시물 내용";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title(expectedTitle)
                .body(expectedBody)
                .build();

        String url = "http://localhost:" + port + "/contents/" + updatedId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postDao.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getBody()).isEqualTo(expectedBody);

    }




}
