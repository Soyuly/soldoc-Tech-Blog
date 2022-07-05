package com.soldoc.tech.domain.banner;


import com.soldoc.tech.domain.banner.dao.BannerDao;
import com.soldoc.tech.domain.banner.model.Banner;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BannerDaoTest {

    @Autowired
    BannerDao bannerDao;

    @After
    public void cleanup(){
        bannerDao.deleteAll();
    }

    @Test
    public void 배너저장_불러오기() {
        //given
        String url = "https://kr.lovepik.com/image-401792155/heart-to-heart.html";

        bannerDao.save(Banner.builder()
                .url(url)
                .build());
        //when
        List<Banner> BannerList = bannerDao.findAll();

        //then
        Banner banners = BannerList.get(0);
        assertThat(banners.getUrl()).isEqualTo(url);
    }

}
