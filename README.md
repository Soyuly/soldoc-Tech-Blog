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
http://127.0.0.1:9000/api/contents/{id}
```

#### Body
```
{
  "title" : "월요일",
  "body" : "반갑습니다"
}
```

#### Reponse
```
{
    "title": "월요일",
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
http://127.0.0.1:9000/api/contents/{id}
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

