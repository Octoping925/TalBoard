# API 명세서

목차
---

1. [사용자 관련](#사용자-관련)
2. [글 관련](#글-관련)
3. [파일 관련](#파일-관련)


## 사용자 관련
TalBoard의 사용자와 관련한 모든 API에 관련한 정보입니다.


### API 정보
* **/members/login** [POST] : 로그인
  - 매개변수
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다. 최대 30자의 이메일 형식입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다. 최대 30자입니다.
  - 응답
    + member_no *(int)*  :  사용자의 고유한 멤버 번호입니다.

* **/members/logout** [GET] : 로그아웃
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + 미정

* **/members/regist** [POST] : 회원가입
  - 매개변수
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다. 최대 30자의 이메일 형식입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다. 최대 30자입니다.
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다. 최대 30자입니다.
  - 응답
    + 미정

* **/members/resign** [DELETE] : 회원탈퇴
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + 미정

* **/members/find/id** [GET] : 아이디 찾기
  - 매개변수
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다. 최대 30자입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다. 최대 30자입니다.
  - 응답
    + id *(String)*  : 로그인에 필요한 아이디입니다. 최대 30자의 이메일 형식입니다.

* **/members/find/password** [GET] : 비밀번호 찾기
  - 매개변수
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다. 최대 30자의 이메일 형식입니다.
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다. 최대 30자입니다.
  - 응답
    + 미정

* **/members/email** [PATCH] : 이메일 변경
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다. 최대 30자입니다.
  - 응답
    + 미정

* **/members/password** [PATCH] : 비밀번호 변경
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다. 최대 30자입니다.
  - 응답
    + 미정

* **/members/like** [GET] : 사용자가 추천한 글 목록 조회
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + post *(obj[])*  : 게시글의 정보가 담긴 객체들로 이루어진 배열입니다.

* **/members/like/{post_no}** [GET] : 사용자의 특정 게시글 추천 여부 조회
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + like_status *(int)*  :  1은 추천, -1은 비추천, 0은 미반응을 의미합니다.






## 글 관련
TalBoard의 게시글과 관련한 모든 API에 관련한 정보입니다.


### API 정보
* **/posts** [GET] : 전체 글 목록 조회
  - 매개변수
    + page_size *(int)*  : 글을 한번에 가져올 개수입니다. 기본값은 30입니다.
    + page_cnt *(int)*  :  글을 가져올 페이지 번호입니다. 기본값은 1입니다.
  - 응답
    + post *(obj[])*  :  게시글의 정보가 담긴 객체들로 이루어진 배열입니다.

* **/posts/{posts_no}** [GET] : 게시글 상세 조회
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + title *(String)*  :  게시글의 제목입니다.
    + context *(String)*  :  게시글의 내용입니다.
    + 파일 정보 등등 미정
    + create_date *(date)*  :  게시글의 작성 시각입니다.

* **/posts/{posts_no}** [PATCH] : 게시글 수정
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
    + title *(String, 필수)*  :  게시글의 제목입니다.
    + context *(String)*  :  게시글의 내용입니다.
    + 파일 정보 등등 미정
  - 응답
    + 미정

* **/posts/{posts_no}** [DELETE] : 게시글 삭제
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
  - 응답
    + 미정


* **/posts/{posts_no}/like** [GET] : 게시글 추천 / 비추천 수 조회
  - 매개변수
    + 없음
  - 응답
    + like_cnt *(int)*  :  추천 수
    + dislike_cnt *(int)*  :  비추천 수

* **/posts/{posts_no}/like** [POST] : 게시글 추천 / 비추천
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
    + like_status *(int)*  :  1은 추천, -1은 비추천, 0은 미반응을 의미합니다.
  - 응답
    + 미정


* **/posts/{posts_no}/comment** [POST] : 댓글 작성
  - 매개변수
    + member_no *(int, 필수)*  :  사용자의 고유한 멤버 번호입니다.
    + context *(String, 필수)*  :   댓글의 내용입니다.
    + emoticon *(int)*  :  댓글에 첨부할 이모티콘의 번호입니다.
    + parent_no *(int)*   :  해당 댓글이 답글일 경우 부모 댓글의 고유 번호입니다.
  - 응답
    + comment_no *(int)*  :  댓글의 고유 번호입니다.

* **/posts/{posts_no}/comment** [GET] : 댓글 조회
  - 매개변수
    + 없음
  - 응답
    + 미정
