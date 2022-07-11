package com.soldoc.tech.domain.Theme;

import com.soldoc.tech.domain.theme.dao.ThemeDao;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThemeApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ThemeDao themeDao;

    @After
    public void tearDown() throws Exception{
        themeDao.deleteAll();
    }

    @Test
    public void theme_등록된다() throws Exception{
        //given
        String name = "React Framework";

        ThemeSaveRequestdDto themeSaveRequestdDto = ThemeSaveRequestdDto.builder()
                .name(name)
                .build();

        String url = "http://localhost:" + port + "/api/theme";

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, themeSaveRequestdDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Theme> all = themeDao.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
    }
}
