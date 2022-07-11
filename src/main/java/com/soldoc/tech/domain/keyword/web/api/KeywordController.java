//package com.soldoc.tech.domain.keyword.web.api;
//
//
//import com.soldoc.tech.domain.keyword.Service.KeywordService;
//import com.soldoc.tech.domain.keyword.model.Keyword;
//import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchReqDto;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@RestController
//public class KeywordController {
//    private final KeywordService keywordService;
//
//    @GetMapping("/api/keyword")
//    public List<Keyword> showKeyword(){
//        return keywordService.showKeyword();
//    }
//
////    @PostMapping("/api/keyword")
////    public void save(@RequestBody KeywordSearchReqDto keywordSearchReqDto){
////        keywordService.save(keywordSearchReqDto);
////    }
//
//
//
//
//}
