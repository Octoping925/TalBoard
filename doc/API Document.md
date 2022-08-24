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
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다.
  - 응답
    + 200 - OK : 로그인 성공
    + 400 - Bad Request : 로그인 실패

* **/members/logout** [GET] : 로그아웃
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 로그아웃 성공
    + 404 - Not Found : 회원 정보 찾지 못함

* **/members/regist** [POST] : 회원가입
  - 매개변수
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다.
    + password *(String, 필수)*  :  로그인에 필요한 비밀번호입니다.
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다.
  - 응답
    + 200 - OK : 회원 가입 성공
    + 400 - Bad Request : 아이디, 비밀번호 유효성 검사 실패
    + 409 - Conflict : 이미 존재하는 아이디 또는 이메일

* **/members/resign** [DELETE] : 회원탈퇴
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + resign_member_no *(int, 필수)*   :  탈퇴시킬 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 회원 탈퇴 성공
    + 403 - Forbidden : 회원 탈퇴 권한 없음
    + 404 - Not Found : 회원 정보 찾지 못함

* **/members/find/id** [GET] : 아이디 찾기
  - 매개변수
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다.
  - 응답
    + 200 - OK : 아이디 찾기 성공
    + 400 - Bad Request : 회원 정보 찾지 못함
  - 데이터
    + id *(String)*  : 로그인에 필요한 아이디입니다.

* **/members/find/password** [GET] : 비밀번호 찾기
  - 매개변수
    + id *(String, 필수)*  : 로그인에 필요한 아이디입니다.
    + email_addr *(String, 필수)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다.
  - 응답
    + 200 - OK : 비밀번호 찾기 성공
    + 400 - Bad Request : 회원 정보 찾지 못함
  - 데이터
    + 비밀번호가 작성한 이메일로 전송됩니다.

* **/members/accountInfo** [PATCH] : 계정 정보 변경
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + password *(String)*  :  로그인에 필요한 비밀번호입니다.
    + email_addr *(String)*  : 아이디와 비밀번호 찾기 시 이용할 수 있는 이메일 주소입니다.
  - 응답
    + 200 - OK : 변경 성공
    + 400 - Bad Request : 비밀번호 또는 메일 유효성 검사 실패
    + 404 - Not Found : 회원 정보 찾지 못함
    + 409 - Conflict : 동일한 이메일 존재

* **/members/like** [GET] : 사용자가 추천한 글 목록 조회
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 회원 정보 찾지 못함
  - 데이터
    + post *(post[])*  : 게시글의 정보가 담긴 객체들로 이루어진 배열입니다.

* **/members/like/{post_no}** [GET] : 사용자의 특정 게시글 추천 여부 조회
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 회원 정보 또는 게시글 정보 찾지 못함
  - 데이터
    + like_status *(int)*  :  1은 추천, -1은 비추천, 0은 미반응을 의미합니다.

* **/members/block** [GET] : 사용자가 차단한 사용자들의 정보 조회
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 400 - Bad Request : 회원 정보 찾지 못함
  - 데이터
    + blocked_member *(member[])*  :  회원 정보가 담긴 객체로 이루어진 배열입니다.

* **/members/block** [POST] : 사용자 차단
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + block_member_no *(int, 필수)*  : 차단할 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 차단 성공
    + 400 - Bad Request : 이미 차단 중인 회원
    + 404 - Not Found : 회원 정보 또는 게시글 정보 찾지 못함

* **/members/block** [DELETE] : 사용자 차단 해제
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + block_member_no *(int, 필수)*  : 차단할 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 차단 해제 성공
    + 400 - Bad Request : 차단하지 않은 회원
    + 404 - Not Found : 회원 정보 또는 게시글 정보 찾지 못함






## 글 관련
TalBoard의 게시글과 관련한 모든 API에 관련한 정보입니다.


### API 정보
* **/posts/regist** [POST] : 게시글 작성
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호 입니다.
    + title *(String, 필수)* : 게시글 제목 입니다.
    + context *(String, 필수)* : 게시글 내용 입니다.
    + 파일 정보 등등 미정
  - 응답
    + 200 - OK : 게시글 작성 성공
    + 400 - Bad Request : 데이터 유효셩 검사 실패
    + 401 - Unauthorized  : 게시글 작성 권한 없음
    + 404 - Not Found : 게시글 정보를 찾지 못함

* **/posts** [GET] : 전체 글 목록 조회
  - 매개변수
    + 없음
    <!-- + page_size *(int)*  : 글을 한번에 가져올 개수입니다. 기본값은 30입니다.
    + page_cnt *(int)*  :  글을 가져올 페이지 번호입니다. 기본값은 1입니다.
    이거 들어가면 400, 404 오류 추가 -->
  - 응답
    + 200 - OK : 조회 성공
  - 데이터
    + post *(post[])*  :  게시글의 정보가 담긴 객체들로 이루어진 배열입니다.

* **/posts/{posts_no}** [GET] : 게시글 상세 조회
  - 매개변수
    + 없음
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 게시글 조회 실패
  - 데이터
    + title *(String)*  :  게시글의 제목입니다.
    + context *(String)*  :  게시글의 내용입니다.
    + file *(file[])*  :  게시글에 첨부된 파일의 정보가 담긴 객체로 이루어진 배열입니다.
    + create_date *(date)*  :  게시글의 작성 시각입니다.

* **/posts/{posts_no}** [PATCH] : 게시글 수정
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + title *(String, 필수)*  :  게시글의 제목입니다.
    + context *(String)*  :  게시글의 내용입니다.
    + 파일 정보 등등 미정
  - 응답
    + 200 - OK : 수정 성공
    + 400 - Bad Request : 데이터 유효성 검사 실패
    + 401 - Unauthorized : 수정 권한 없음
    + 404 - Not Found : 게시글 조회 실패

* **/posts/{posts_no}** [DELETE] : 게시글 삭제
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 삭제 성공
    + 401 - Unauthorized : 삭제 권한 없음
    + 404 - Not Found : 게시글 조회 실패

* **/posts/{posts_no}/like** [GET] : 게시글 추천 / 비추천 수 조회
  - 매개변수
    + 없음
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 게시글 조회 실패
  - 데이터
    + like_cnt *(int)*  :  추천 수
    + dislike_cnt *(int)*  :  비추천 수

* **/posts/{posts_no}/like** [POST] : 게시글 추천 / 비추천
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + like_status *(int, 필수)*  :  1은 추천, -1은 비추천, 0은 미반응을 의미합니다.
  - 응답
    + 200 - OK : 추천 성공
    + 400 - Bad Request : 데이터 유효성 검사 실패
    + 404 - Not Found : 게시글 조회 실패

* **/posts/{posts_no}/comment** [POST] : 댓글 작성
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + context *(String, 필수)*  :   댓글의 내용입니다.
    + emoticon *(int)*  :  댓글에 첨부할 이모티콘의 번호입니다.
    + parent_no *(int)*   :  해당 댓글이 답글일 경우 부모 댓글의 고유 번호입니다.
  - 응답
    + 200 - OK : 작성 성공
    + 400 - Bad Request : 데이터 유효성 검사 실패
    + 404 - Not Found : 게시글 조회 실패
  - 데이터
    + comment_no *(int)*  :  댓글의 고유 번호입니다.

* **/posts/{posts_no}/comment** [GET] : 게시글 댓글 목록 조회
  - 매개변수
    + 없음
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 게시글 조회 실패
  - 데이터
    + comment *(comment[])*  :  댓글의 정보가 담긴 객체들로 이루어진 배열입니다.

* **/posts/{post_no}/comment** [PATCH] : 댓글 수정
  - 매개변수
    + member_no *(int, 필수)* : 사용자의 고유한 멤버 번호입니다.
    + context *(String, 필수)*  :   댓글의 내용입니다.
    + emoticon *(int)*  :  댓글에 첨부할 이모티콘의 번호입니다.
  - 응답
    + 200 - OK : 수정 성공
    + 400 - Bad Request : 데이터 유효성 검사 실패
    + 401 - Unauthorized : 수정 권한 없음
    + 404 - Not Found : 게시글 조회 실패

* **/posts/{post_no}/comment** [DELETE] : 댓글 삭제
  - 매개변수
    + member_no *(int)* : 사용자의 고유한 멤버 번호입니다.
    + comment_no *(int, 필수)* : 댓글의 고유 번호입니다.
  - 응답
    + 200 - OK : 삭제 성공
    + 401 - Unauthorized : 삭제 권한 없음
    + 404 - Not Found : 게시글 또는 댓글 조회 실패

* **/posts/{post_no}/report** [POST] : 게시글 신고
  - 매개변수
    + member_no *(int)* : 사용자의 고유한 멤버 번호입니다.
  - 응답
    + 200 - OK : 신고 성공
    + 404 - Not Found : 사용자 또는 게시글 번호 조회 실패

* **/posts/search** [POST] : 게시글 검색
  - 매개변수
    + member_no *(String, 필수)*  : 로그인에 필요한 아이디입니다.
  - 응답
    + 200 - OK : 검색 성공
    + 404 - Not Found : 검색 실패

* **/posts/report** [POST] : 신고된 글 목록 조회
  - 매개변수
    + member_no *(String, 필수)*  : 로그인에 필요한 아이디입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 조회 실패



## 파일 관련
TalBoard의 파일과 관련한 모든 API에 관련한 정보입니다.


### API 정보
* **/files/upload** [POST] : 파일 업로드
  - 매개변수
    + posts_no *(int, 필수)*  :  파일을 첨부할 게시글의 고유 번호입니다.
    + 미정
  - 응답
    + 200 - OK : 업로드 성공
    + 400 - Bad Request : 파일 정보 유효성 검사 실패
    + 404 - Not Found : 게시글 조회 실패

* **/files/info** [GET] : 게시글에 첨부된 파일 목록 정보 조회
  - 매개변수
    + posts_no *(int, 필수)*  :  게시글의 고유 번호입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 조회 실패
  - 데이터
    + file *(file[])*  :  파일의 정보가 담긴 객체로 이루어진 배열입니다.

* **/files/download** [GET] : 파일 다운로드
  - 매개변수
    + file_no *(int, 필수)*  :  파일의 고유 번호입니다.
  - 응답
    + 200 - OK : 조회 성공
    + 404 - Not Found : 조회 실패
  - 데이터
    + 파일이 다운로드 됩니다.

