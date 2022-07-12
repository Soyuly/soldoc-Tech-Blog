package com.soldoc.tech;


import com.soldoc.tech.oauth.config.properties.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//@EnableJpaAuditing: JPA Auditing 어노테이션들(누가 값 변경, 언제)에 대한 모든 감시기능을 활성화할 수 있도록 활성화 어노테이션 추가
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
@SpringBootApplication
public class SoldocBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoldocBlogApplication.class, args);
    }
}