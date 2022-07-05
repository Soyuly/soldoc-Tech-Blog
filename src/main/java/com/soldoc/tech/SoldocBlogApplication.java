package com.soldoc.tech;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//@EnableJpaAuditing: JPA Auditing 어노테이션들을 모두 활성화할 수 있도록 활성화 어노테이션 추가
@EnableJpaAuditing
@SpringBootApplication
public class SoldocBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoldocBlogApplication.class, args);
    }

}
