# Soldoc-Tech API

### findAllContent()
- 요청방식 : get
- 기능 : 게시물들의 목록을 보여준다.
- url : http://127.0.0.1:9000/api/
- 예시
#### Request
```
http://127.0.0.1:9000/api
```

#### Response
```
HTTP 200 OK
Allow: GET, OPTIONS
Content-Type: application/json
Vary: Accept

[
    {
        "title": "안녕",
        "body": "안녕하세요",
        "author": "정윤수",
        "likeCount": 0,
        "viewCount": 0,
        "postKeywords": [],
        "createdDate": "2022-07-11T10:55:20.052",
        "modifiedDate": "2022-07-11T10:55:20.052"
    },
    {
        "title": "안녕",
        "body": "안녕하세요",
        "author": "정윤수",
        "likeCount": 0,
        "viewCount": 0,
        "postKeywords": [],
        "createdDate": "2022-07-11T10:48:16.487",
        "modifiedDate": "2022-07-11T10:48:16.487"
    }
]
```
<hr/>

### savePost()
- 요청방식 : post
- 기능 : 새로운 게시글을 생성한다.
- url : http://127.0.0.1:9000/api/contents
- 예시
#### Request
```
http://127.0.0.1:9000/api/save
```

#### Body
```
{
    "title": "안녕",
    "body": "안녕하세요",
    "author": "정윤수"
}
```

#### Reponse
```
{
        "title": "안녕",
        "body": "안녕하세요",
        "author": "정윤수",
        "likeCount": 0,
        "viewCount": 0,
        "postKeywords": [],
        "createdDate": "2022-07-11T10:55:20.052",
        "modifiedDate": "2022-07-11T10:55:20.052"
}
```
<hr/>

### updatePosts()
- 요청방식 : put
- 기능 : 게시물을 수정한다.
- url : http://127.0.0.1:9000/api/update/{id}
- 예시
#### Request
```
http://127.0.0.1:9000/api/contents/1
```

#### Body
```
{
  "title" : "솔닥",
  "body" : "반갑습니다"
}
```

#### Reponse
```
{
    "title": "솔닥",
    "body": "반갑습니다",
    "author": "정윤수",
    "likeCount": 0,
    "viewCount": 0,
    "postKeywords": [],
    "createdDate": "2022-07-11T10:48:16.487",
    "modifiedDate": "2022-07-11T11:01:48.119"
}
```
<hr/>

### deletePost()
- 요청방식 : delete
- 기능 : 게시물을 삭제한다.
- url : http://127.0.0.1:9000/api/delete/{id}
- 예시
#### Request
```
http://127.0.0.1:9000/api/contents/1
```

#### Reponse
```
{
    "message": "sucess",
    "code": 200
}
```
<hr/>

### findById()
- 요청방식 : get
- 기능 : 해당 아이디에 해당하는 게시글을 가져온다.
- url : http://127.0.0.1:9000/api/contents/{id}
- 예시
#### Request
```
http://127.0.0.1:9000/api/1
```

#### Reponse
```
{
    "title": "안녕",
    "body": "안녕하세요",
    "author": "정윤수",
    "likeCount": 0,
    "viewCount": 0,
    "postKeywords": [],
    "createdDate": "2022-07-11T10:55:20.052",
    "modifiedDate": "2022-07-11T10:55:20.052"
}
```

---
### search()
* 요청방식: get
* 기능: 게시물의 제목 또는 내용에서 사용자가 입력한 검색어가 포함된 게시글을 불러온다.
* url: http://127.0.0.1:9000/api/contents
* 예시

#### Request
```
http://127.0.0.1:9000/api/contents/search
```

#### Body
```
{
    "word": "안녕"
}
```

#### Response
```
{
    "title": "안녕",
    "body": "안녕하세요",
    "author": "정윤수",
    "likeCount": 0,
    "viewCount": 0,
    "postKeywords": [],
    "createdDate": "2022-07-11T10:55:20.052",
    "modifiedDate": "2022-07-11T10:55:20.052"
}
```


---

addLike()
* 요청방식: post
* 기능: 해당 게시물의 좋아요 갯수를 하나 증가시킨다.
* url: http://127.0.0.1:9000/api/contents/{id}/addLike
* 예시


#### Request
```
http://127.0.0.1:9000/api/contents/1/addLike
```

#### Response
```
{
    "message": "sucess",
    "code": 200
}
```
---
deleteLike()
* 요청방식: post
* 기능: 해당 게시물의 좋아요 갯수를 하나 감소시킨다.
* url: http://127.0.0.1:9000/api/contents/{id}/deleteLike
* 예시


#### Request
```
http://127.0.0.1:9000/api/contents/1/deleteLike
```

#### Response
```
{
    "message": "sucess",
    "code": 200
}
```


### public Long save(ThemeSaveRequestdDto themeSaveRequestdDto)
- 요청방식 : post
- 기능 : 키워드 테마를 추가한다.
- url : http://localhost:9000/api/theme
- 예시
#### Request
```
http://localhost:9000/api/theme
```
#### body
```
{
    "name" : "React"
}
```
#### Response
```
themeId
```
<hr/>

###  public PostVO save(@RequestBody PostVO postResponse)
- 요청방식 : post
- 기능 : Post 테이블에 게시글을 저장하고, 해당 Theme에 맞게 키워드도 각각 저장시킨다.
- url : http://localhost:9000/api/postsy
- 예시
#### Request
```
http://127.0.0.1:8000/api/create/
```

#### Body
```
{
    "title" : "CSR에 SEO 적용하기",
    "body" : "React Helmet 라이브러리를 통해서 적용",
    "author" : "soyul",
    "keywords" : ["react","helmet","seo"],
    "theme" : "React"

}
```

#### Reponse
```
{
    "title": "CSR에 SEO 적용하기",
    "body": "React Helmet 라이브러리를 통해서 적용",
    "author": "soyul",
    "keywords": [
        "react",
        "helmet",
        "seo"
    ],
    "theme": "React"
}
```
<hr/>

### public ArrayList<String> getKewordById(@PathVariable("id") Long themeId)
- 요청방식 : GET
- 기능 : 특정 테마에 포함되는 모든 키워드들을 가져온다.
- url : http://localhost:9000/api/keywords/{theme_id}
- 예시
#### Request
```
http://localhost:9000/api/keywords/1
```

#### Response
```
[
    "react",
    "helmet",
    "seo"
]
```
<hr/>


## Authroization Api

### authorizationEndpoint()
- 요청방식 : GET
- 기능 : 구글 로그인에 성공하면, Redirect URI를 통해 토큰을 반환한다.
- url : http://localhost:9000/oauth2/authorization/google
- redirect url : http://localhost:9000/oauth2/code/google
- 예시
#### Request
```
http://localhost:9000/oauth2/authorization/google
```

#### Redirect
```
http://localhost:9000/?token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTQyMDcyMzU3ODU5OTQwMjU1MDYiLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjU4MzY5Mzk1fQ.2_bHVRbpoTwqpkI8kZZLAeC1Wbk4URqlPcvCSyrjlZc
```
<hr/>

### public String test()
- 요청방식 : get
- 기능 : 로그인 한 유저의 Refresh Token과 Access token이 반환된다.
- url : http://127.0.0.1:8000/api/auth/test
- 예시
#### Request
```
http://127.0.0.1:8000/api/auth/test
```

#### Reponse
```
 {
    "access_token ": eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTQyMDcyMzU3ODU5OTQwMjU1MDYiLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjU4MzY4NzkwfQ.hD-9R6Eh_iB2390RqxoG7KhkhDmWBk0ShKGY1VDjsjs,
    "refresh_token ": eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5MjZEOTZDOTAwMzBERDU4NDI5RDI3NTFBQzFCREJCQyIsImV4cCI6MTY1ODEwOTU5MH0.v97ISC2HBKSKAe3l4OvgnyZnwJmHgWL_5KWHK1ckxPA
 }
```
<hr/>

### public ApiResponse refreshToken()
- 요청방식 : get
- 기능 : 만료된 Access Token이면, refresh token을 통해 Access Token을 갱신한다.
- url : http://localhost:9000/api/auth/refresh
- 예시
#### Request
```
http://localhost:9000/api/auth/refresh
```

#### Reponse
```
{
    "header": {
        "code": 500,
        "message": "Not expired token yet."
    },
    "body": null
}
```
<hr/>

### public ApiResponse getUser()
- 요청방식 : get
- 기능 : 토큰을 통해 로그인한 유저의 정보를 가져온다.
- url : http://localhost:9000/api/auth/refresh
- 예시
#### Request
```
http://localhost:9000/api/auth/refresh
```

#### Reponse
```
{
    "header": {
        "code": 200,
        "message": "SUCCESS"
    },
    "body": {
        "user": {
            "userId": "114207235785994025506",
            "username": "SoYul Yang",
            "email": "soyul8147@gmail.com",
            "emailVerifiedYn": "Y",
            "profileImageUrl": "https://lh3.googleusercontent.com/a/AItbvmlQQQ8UgYCyTGGRk_CFOcW9auxtuMH84Q-eU_Kk=s96-c",
            "providerType": "GOOGLE",
            "roleType": "USER",
            "createdAt": "2022-07-11T11:09:55.124",
            "modifiedAt": "2022-07-11T11:09:55.124"
        }
    }
}
```
<hr/>
