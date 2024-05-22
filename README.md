**Kimstagram (2024. 01.)**

Instagram의 기능을 클론하여 나만의 SNS를 제작했던 개인 사이드 프로젝트.

**사용한 기술 스택**
- SpringBoot
- MySQL와 JPA
- HTML, CSS
- BootStrap
- JavaScript

**구현한 기능**
- 회원가입 기능 (JPA)
- Spring Security를 활용한 보안 로그인
- Jwt토큰을 통한 Stateless 세션 유지
- 액세스 토큰과 리프레시 토큰 분리를 통해 보안 강화
- MultipartFile을 활용한 이미지 업로드 (동적으로 이미지 보관)
- 유저 프로필 조회
- 게시물 조회 → 사진이 여러 개일 경우 prev, next 버튼으로 사진 넘기기
- ajax를 활용하여 동적으로 게시물에 댓글 남기고 조회
- 게시물 좋아요 기능과 좋아요 한 유저 목록 조회
- 프로필 이미지, 한 줄 커멘트 수정
- 유저 간 팔로우 기능
- 닉네임으로 유저 프로필 검색
- OAuth2를 활용한 페이스북 로그인 → 페이스북 로그인 후 Jwt 토큰 발급
- 웹소켓을 활용한 DM 구현 (online/offline)
- 팔로우한 유저의 게시물을 index에 띄우기

**API 명세**
(API가 워낙 많아서, 간단한 기능들만 작성)
|URI|함수|메소드|기능|요청 데이터|응답 데이터|기타|
|---|---|-------------|---|-------------|-------------|---|
|/checkToekn|intoIndex|POST|매번 사이트를 로딩할 때마다, 헤더에 담긴 JWT 액세스 토큰이 유효한지 검사|(헤더)String Authorization: 액세스 토큰|유효 시 "Token_Success"||
|/getFollowPosts|getFollowPosts|POST|홈 화면에서 팔로우 중인 계정의 포스트를 가져온다|int principalId: 현재 접속 중인 계정의 ID|포스트 배열||
|/upload|upload|POST|사진과 글을 자신의 계정에 게시한다|int userId: 포스트를 게시할 계정의 ID<br>String comment: 포스트에 남길 글<br>List pictures: 게시할 사진(최대 10장)|||
|/follow|follow|POST|특정 유저를 팔로우한다||||
|/setUsername|setUsername|POST|계정 이름을 변경한다|int oriusername: 기존 username<br>int newusername: 바꿀 username|||
|/saveDmMessage|saveDmMessage|POST|DM을 보내고 DB에 메시지를 저장한다||||

자세히 보기: https://kimdev-s.tistory.com/172

