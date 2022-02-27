![image](https://user-images.githubusercontent.com/28949166/154533487-03a0798a-aad0-44ab-891d-0f7934eb9178.png)

# 👉[독서 습관 형성 플랫폼, 북적북적 HERE](https://i6a305.p.ssafy.io/)👈

## Team 투머치토5커

<!-- ![image](https://user-images.githubusercontent.com/28949166/154533716-08ba1ee2-21e0-417d-a338-5cc6552fcb65.png) -->

| 이름    | 김경석                                                                                                          | 김수민                                                                                                          | 김은선                                                                                                          | 이재경                                                                                                          | 형다은                                                                    | 노하윤                                                                                                          |
| ------- | --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| Profile | ![image](https://user-images.githubusercontent.com/68716284/154608916-64c9f584-22e3-477e-b2f3-15ca6a13b15d.png) | ![image](https://user-images.githubusercontent.com/28949166/154539841-116c2120-c8be-4df8-acf9-d8c57fb5bdd0.png) | ![image](https://user-images.githubusercontent.com/28949166/154540246-0f8a45bf-3966-4e4b-a484-4750e41aee9f.png) | ![image](https://user-images.githubusercontent.com/28949166/154540445-ac05c003-dc6b-483a-bf05-2f0521535ea2.png) | ![image](https://user-images.githubusercontent.com/28949166/155895219-f92ab09e-1404-475e-9b47-a20ff111c52d.png) | ![image](https://user-images.githubusercontent.com/28949166/154539893-51ffe56f-57a1-4c6f-98c2-303395f8176a.png) 
| Stack   | 백엔드 API 개발 & 서버 관리 및 배포                                                                             | Frontend & UI/UX                                                                                                | 백엔드 API 개발 & Openvidu                                                                                      | 백엔드 API 개발 & Spring Security                                                                               | 팀장 & Frontend & UI/UX & Openvidu-react & Redux 개발                     | Frontend & UI/UX                                                                                                |
| Git     | [@0xe82de](https://github.com/0xe82de)                                                                                                       | [@sumini97](https://github.com/sumini97)                                                                                                       | [@aucro](https://github.com/aucro)                                                                                                       | [@aletsire](https://github.com/aletsire)                                                                                                       | [@unain-dev](https://github.com/unain-dev)                                | [@rohhy1120](https://github.com/rohhy1120)                                                                                                       |

## 💡 [북적북적 서비스 소개 보러가기](https://youtu.be/TIrq-nJfRB8) 💡
## 🎞 [북적북적 서비스 시연 보러가기](https://youtu.be/aAUdACz40JM) 🎞

---

![image](https://user-images.githubusercontent.com/28949166/155871243-f59a22e2-4cbb-4ad9-be1d-05dc421ab17d.png)
![image](https://user-images.githubusercontent.com/28949166/155871253-ebf876c5-8ae7-4f59-8ade-5706b6ae4d73.png)
![image](https://user-images.githubusercontent.com/28949166/155871268-c91d0b6d-180d-48bc-91f6-121b2814fe20.png)
![image](https://user-images.githubusercontent.com/28949166/155871272-e105b07b-e0b0-4400-ae95-e478277cb3de.png)
![image](https://user-images.githubusercontent.com/28949166/155871275-38d830b5-958b-4e91-af0e-5efcf13b88e3.png)

---

## Development Stack

![image](https://user-images.githubusercontent.com/28949166/155871280-905d2cd5-8a1f-46b9-b331-116f5253c2b3.png)

<details>
<summary>협업</summary>
<div markdown="1">
<ul>
<li>Jira</li>
<li>Gitlab</li>
<li>Notion</li>
<li>Slack</li>
</ul>
</div>
</details>

<details>
<summary>Frontend</summary>
<div markdown="1">       
  <ul>
  <li>React.js</li>
  <li>Styled-components</li>
  <li>Redux</li>
<li><ul>library<ul></li>
<li>axios</li>
  <li>react-modal</li>
<li>react-js-pagination</li>
<li>qs</li>
<li>openvidu-react</li>
  </ul>
</div>
</details>

<details>
<summary>Backend</summary>
<div markdown="1">       
    <ul>
  <li>Spring Boot</li>
  <li>JPA</li>
<li>Spring MVC</li>
<li>Spring Security</li>
<li>JUnit5</li>
<li>JWT</li>
<li>p6spy</li>
<li>Lombok</li>
<li>Mysql</li>
<li>EC2 Ubuntu Server</li>
<li>InjelliJ, Postman, Datagrip, Mysql workbench, Nortion, Sourcetree</li>
  </ul>
</div>
</details>

## Deployment Flow

![image](https://user-images.githubusercontent.com/28949166/155871284-14ca93ed-3930-48f2-bb65-6199033bba6e.png)

---

## 설치 및 사용

### Github Clone

```
    git clone
```

### Frontend

- 시스템 버전

  - node : @16.13.0
  - npm : @8.1.0

- /frontend 디렉토리로 이동
- React 모듈 설치
  ```
    npm install
  ```
- /frontend/src/common/config/key에 reading_group.js 파일 생성해 아래 코드 추가

  ```
  const OPENVIDU_URL = "https://i6a305.p.ssafy.io:7443";
  const OPENVIDU_SECRET = "MY_SECRET";

  export { OPENVIDU_SECRET, OPENVIDU_URL };

  ```

- React 어플리케이션 실행
  ```
    npm start
  ```

### Backend

- 시스템 버전

  - JVM: AWS Corretto 11
  - mysql: 8.0.28

- 소스코드 pull

  ```
  git pull origin develop
  ```

- 빌드, 배포 스크립트 실행

  ```
  sh ~/S06P12A305/scripts/deploy.sh
  ```

  

---

## 개발 스프린트

| 스프린트                                    | 기간                    | FE                                                                                                                                                                                                                           | BE                                                                                                                                                                                                                                                                                                                 |
| ------------------------------------------- | ----------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 프로젝트 기획 <br>&<br> git, Jira 전략 모색 | 2022.01.10 - 2022.01.23 | <ul><li>Figma를 이용한 와이어프레임 작성</li><li>프로젝트 기획</li><ul>                                                                                                                                                      | <ul><li>엔티티 설계</li><li>프로젝트 기획</li><ul>                                                                                                                                                                                                                                                                 |
| 기본 기능 개발                              | 2022.01.24 - 2022.01.30 | <ul><li>회원가입</li><li>로그인, 로그 아웃</li><li>회원정보 조회</li><ul>                                                                                                                                                    | <ul><li>로그인 API 개발</li><li>회원정보 수정 API 개발</li><li>CORS 필터 추가</li><ul>                                                                                                                                                                                                                             |
| 주요 기능 개발                              | 2022.01.31 - 2022.02.06 | <ul><li>Redux 세팅</li><li>로그인 리팩토링</li><li>홈 화면 재구성</li><li>북로그 작성 페이지 개발</li><li>나의 북로그 조회 페이지 개발</li><li>책 목록 조회 페이지 개발</li><ul>                                             | <ul><li>책 정보 임시 데이터 구축</li><li>책 상세페이지 API 개발</li><li>책 리뷰 API 개발</li><li>북로그 API 개발</li><li>북로그 조회 API 개발</li><ul>                                                                                                                                                             |
| 심화 기능 개발                              | 2022.02.07 - 2022.02.13 | <ul><li>북로그 좋아요 기능 개발</li><li>공개 북로그 상세페이지 조회 개발</li><li>책 리뷰 수정, 삭제 기능 구현</li><li>나의 북로그 목록 화면 개발</li><li>화상회의 화면 렌더링</li><li>화상 회의(Openvidu) 모듈 연결</li><ul> | <ul><li>북로그 좋아요 API 개발</li><li>Openvidu와 프로젝트 연동</li><li>SSL 적용</li><li>책 상세페이지 조회 별점 기능 추가</li><li>독서 모임 별 게시판 API 개발</li><li>책 - 북로그 목록 연계 조회 기능 개발</li><li>배포 스크립트 작성</li><li>독서 모임 모집 API 개발</li><ul>                                   |
| 추가 기능 개발 & 리팩토링                   | 2022.02.14 - 2022.02.18 | <ul><li>독서모임 참여 신청 기능 개발</li><li>독서모임 리뷰 기능 개발</li><li>챌린지 목록, 상세보기 페이지 구현</li><li>챌린지 참여 신청 구현</li><li>챌린지 인증 기능 개발</li><ul>                                          | <ul><li>활동량, 경험치, 포인트 증감 로직 추가</li><li>독서 모임 참여 API 개발</li><li>독서 모임 팀원 간 리뷰 API 개발</li><li>챌린지 API 개발</li><li>챌린지 참여 신청 API 개발</li><li>챌린지 인증 API 개발</li><li>독서 모임 기간 검사&상태 변경 로직 개발</li><li>챌린지 기간 검사&상태 변경 로직 개발</li><ul> |
