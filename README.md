# Django-RestFramework

### getPosts()
- 요청방식 : get
- 기능 : 게시물들의 목록을 보여준다.
- url : http://127.0.0.1:8000/api/
- 예시
#### Request
```
http://127.0.0.1:8000/api
```

#### Response
```
HTTP 200 OK
Allow: GET, OPTIONS
Content-Type: application/json
Vary: Accept

[
    {
        "id": 1,
        "title": "장고",
        "body": "rest api",
        "author": "소열",
        "pub_date": "2022-07-01T10:49:21.347539Z"
    },
    {
        "id": 2,
        "title": "여러분들 화이팅,,!",
        "body": "세션 잘 하고 있죠,,?",
        "author": "소열",
        "pub_date": "2022-07-01T10:49:50.016594Z"
    },
    {
        "id": 3,
        "title": "아자아자",
        "body": "고고고고고",
        "author": "양소열",
        "pub_date": "2022-07-01T10:50:12.122085Z"
    },
    {
        "id": 4,
        "title": "세영",
        "body": "api테스트중",
        "author": "양소열",
        "pub_date": "2022-07-01T10:50:22.604305Z"
    }
]
```
<hr/>

### createPosts()
- 요청방식 : post
- 기능 : 새로운 게시글을 생성한다.
- url : http://127.0.0.1:8000/api/create/
- 예시
#### Request
```
http://127.0.0.1:8000/api/create/
```

#### Body
```
{
  "title" : "테스트",
  "body" : "post요청",
  "author" : "test"
}
```

#### Reponse
```
{
    "id": 5,
    "title": "테스트",
    "body": "post요청",
    "author": "test",
    "pub_date": "2022-07-01T14:49:38.980197Z"
}
```
<hr/>

### updatePosts()
- 요청방식 : post
- 기능 : 게시물을 수정한다.
- url : http://127.0.0.1:8000/api/update
- 예시
#### Request
```
http://127.0.0.1:8000/api/update
```

#### Body
```
{
  "id" : 5,
  "title" : "테스트 수정하기",
  "body" : "post 수정요청",
  "author" : "test"
}
```

#### Reponse
```
{
    "id": 5,
    "title": "테스트 수정하기",
    "body": "post 수정요청",
    "author": "test",
    "pub_date": "2022-07-01T14:49:38.980197Z"
}
```
<hr/>

### deletePosts()
- 요청방식 : get
- 기능 : 게시물을 수정한다.
- url : http://127.0.0.1:8000/api/delete/게시글번호
- 예시
#### Request
```
http://127.0.0.1:8000/api/update/5
```

#### Reponse
```
{
    "message": "sucess",
    "code": 200
}
```
<hr/>

### getPostById()
- 요청방식 : get
- 기능 : 해당하는 아이디의 게시글을 가져온다.
- url : http://127.0.0.1:8000/api/게시글번호
- 예시
#### Request
```
http://127.0.0.1:8000/api/3
```

#### Reponse
```
{
    "id": 3,
    "title": "아자아자",
    "body": "고고고고고",
    "author": "양소열",
    "pub_date": "2022-07-01T10:50:12.122085Z"
}
```
