# SOLDOC TechBlog API

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