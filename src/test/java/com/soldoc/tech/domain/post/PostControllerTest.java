package com.soldoc.tech.domain.post;


import com.soldoc.tech.domain.post.dao.PostDao;
import com.soldoc.tech.domain.post.model.Post;
import com.soldoc.tech.domain.post.web.dto.PostListResponseDto;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.post.web.dto.PostUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //'진료' 키워드로 검색 테스트
    @Test
    public void Post_검색된다() throws Exception {
        //given
        String expectedTitle = "원격진료솔닥";
        String searchWord = "진료";
        List<Post> postedData;
        postDao.save(Post.builder().title(expectedTitle).body("치료").author("철수").build());
        postDao.save(Post.builder().title("원격솔닥").body("회복").author("영희").build());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("word", "진료");
        final String BASE_URL = "http://localhost:" + 9000 + "/api/contents/search";

        //when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL + "/{word}", String.class, params);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(">>>> " + responseEntity.getBody());

        List<Post> searchData = postDao.findAll();
        System.out.println(searchData);
        assertThat(searchData.get(0).getTitle()).isEqualTo(expectedTitle);

    }

    //BDDMockito 패턴 given, when, then
    @Test
    public void Post_등록된다() throws Exception {
        //given
        String title = "테스트 게시물 제목";
        String body = "테스트 게시물 내용";
        String author = "테스트 게시물 작성자";


        //기본적으로 DTO에 내용을 담아서 테스트.
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .title(title)
                .body(body)
                .author(author)
                .build();

        String url = "http://localhost:" + port + "/api/contents";

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

        String url = "http://localhost:" + port + "/api/contents/" + updatedId;

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
