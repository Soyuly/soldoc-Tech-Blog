package com.soldoc.tech.domain.theme.api;

import com.soldoc.tech.common.PostVO;
import com.soldoc.tech.domain.keyword.Service.KeywordService;
import com.soldoc.tech.domain.keyword.model.Keyword;
import com.soldoc.tech.domain.keyword.web.dto.KeywordSaveRequestDto;
import com.soldoc.tech.domain.post.service.PostService;
import com.soldoc.tech.domain.post.web.dto.PostSaveRequestDto;
import com.soldoc.tech.domain.theme.dto.ThemeResponseDto;
import com.soldoc.tech.domain.theme.dto.ThemeSaveRequestdDto;
import com.soldoc.tech.domain.theme.model.Theme;
import com.soldoc.tech.domain.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThemesController {

    private final PostService postService;
    private final ThemeService themeService;
    private final KeywordService keywordService;

    // Theme Create
    @PostMapping("/api/theme")
    public Long save(@RequestBody ThemeSaveRequestdDto themeSaveRequestdDto){
        return themeService.save(themeSaveRequestdDto);
    }

    // BODY를 각각 파싱해서, 각각의 모델을 생성한다(CREATE)
    @PostMapping("/api/postsy")
    public PostVO save(@RequestBody PostVO postResponse){

        // body에서 넘긴 Name으로 해당하는 Theme 객체를 찿는다.
        Theme theme = themeService.findByName(postResponse.getTheme());

        // Post 객체 생성
        PostSaveRequestDto postSaveRequestDto = PostSaveRequestDto.builder()
                .title(postResponse.getTitle())
                .body(postResponse.getBody())
                .author(postResponse.getAuthor())
                .build();
        postService.save(postSaveRequestDto);

        // 클라이언트에서 키워드를 여러개 보낼 수 있으니까, 리스트로 여러개 받고
        // for문을 통해 디비에 다 추가한다.
        for(String keyword:postResponse.getKeywords()) {
            KeywordSaveRequestDto keywordSaveRequestDto = KeywordSaveRequestDto.builder()
                    .name(keyword)
                    .theme(theme)
                    .build();
            keywordService.save(keywordSaveRequestDto);
        }

        return postResponse;
    }
}
