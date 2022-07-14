package com.soldoc.tech.common;

import com.soldoc.tech.common.PostApiResponseHeader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class PostApiResponse<T> {

    private final static int SUCCESS = 200;

    private final static int CREATED = 201;
    private final static int BAD_REQUEST = 400;
    private final static int UNAUTHORIZED = 401;
    private final static int DIFFRENT_AUTHOR = 402;
    private final static int FORBIDDEN = 403;
    private final static int NOT_FOUND = 404;

    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";

    private final static String CREATED_MESSAGE = "성공적으로 생성되었습니다.";

    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";
    private final static String BAD_REQUEST_MESSAGE = "잘못된 접근입니다.";
    private final static String FORBIDDEN_MESSAGE = "해당 서버에 대한 액세스 권한이 없습니다.";
    private final static String FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";

    private final static String REQUEST_SEARCH = "검색어를 입력 해주세요.";
    private final static String FAILED_SEARCH = "해당하는 검색어를 찾을 수 없습니다.";
    private final static String HAS_NO_POST = "해당하는 게시글이 존재하지 않습니다.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";
    private final static String DIFFRENT_AUTHOR_MESSAGE = "작성자와 해당 user랑 일치하지 않습니다.";




    private final PostApiResponseHeader header;
    private final Map<String, T> body;

    public static <T> PostApiResponse<T> success(String name, T body) {
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new PostApiResponse(new PostApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), map);
    }

    public static <T> PostApiResponse<T> created(String name, T body){
        Map<String, T> map = new HashMap<>();
        map.put(name, body);

        return new PostApiResponse(new PostApiResponseHeader(CREATED, CREATED_MESSAGE), map);
    }

    public static <T> PostApiResponse<T> forbiddenDelete(){
        return new PostApiResponse(new PostApiResponseHeader(FORBIDDEN, FORBIDDEN_MESSAGE), null);
    }

    public static <T> PostApiResponse<T> badRequest() {
        return new PostApiResponse(new PostApiResponseHeader(BAD_REQUEST, BAD_REQUEST_MESSAGE), null);
    }

    public static <T> PostApiResponse<T> hasNoPost() {
        return new PostApiResponse(new PostApiResponseHeader(NOT_FOUND, HAS_NO_POST), null);
    }

    public static <T> PostApiResponse<T> fail() {
        return new PostApiResponse(new PostApiResponseHeader(FAILED, FAILED_MESSAGE), null);
    }

    public static <T> PostApiResponse<T> searchFail() {
        return new PostApiResponse(new PostApiResponseHeader(NOT_FOUND, FAILED_SEARCH), null);
    }

    public static <T> PostApiResponse<T> requestSearch() {
        return new PostApiResponse(new PostApiResponseHeader(BAD_REQUEST, REQUEST_SEARCH), null);
    }



    public static <T> PostApiResponse<T> invalidRefreshToken() {
        return new PostApiResponse(new PostApiResponseHeader(FAILED, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> PostApiResponse<T> notExpiredTokenYet() {
        return new PostApiResponse(new PostApiResponseHeader(FAILED, NOT_EXPIRED_TOKEN_YET), null);
    }

    public static <T> PostApiResponse<T> notAuthor() {
        return new PostApiResponse(new PostApiResponseHeader(UNAUTHORIZED, DIFFRENT_AUTHOR_MESSAGE), null);
    }

}
