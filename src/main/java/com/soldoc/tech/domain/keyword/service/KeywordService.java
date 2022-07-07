//package com.soldoc.tech.domain.keyword.service;
//
//
//import com.soldoc.tech.domain.keyword.dao.KeywordDao;
//import com.soldoc.tech.domain.keyword.web.dto.KeywordSearchResDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class KeywordService {
//    private final KeywordDao keywordDao;
//
//    @Transactional
//    public List<KeywordSearchResDto> search(@RequestBody String keyword) {
//        return keywordDao.findAllSearch(keyword).stream()
//                .map(String::new)
//                .collect(Collectors.toList());
//    }
//}
