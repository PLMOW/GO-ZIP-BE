# 🏘️  GOZIP  
<img width="1502" alt="Pasted image 20230302163342" src="https://user-images.githubusercontent.com/118401337/222367962-8f5f0a43-242b-4db8-b55d-1aedc6d75f01.png">

## 프로젝트 소개  
고승유 매니저님의 내집마련을 위한 집 매물 소개 사이트  

## 팀원 
|이름|이현빈|조성재|한승현|김병무|김대현|  
|--|--|--|--|--|--|
|역할|팀장/BE|BE|BE|팀장/FE|FE|  

## 스택 
### FE
<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=React&logoColor=white"> <img src="https://img.shields.io/badge/TypeScript-3178C6?style=for-the-badge&logo=TypeScript&logoColor=white"> <img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=Redux&logoColor=white"> <img src="https://img.shields.io/badge/styled-components-DB7093?style=for-the-badge&logo=styled-components&logoColor=white"> <img src="https://img.shields.io/badge/Framer-0055FF?style=for-the-badge&logo=Framer&logoColor=white">

<img src="https://img.shields.io/badge/React Query-FF4154?style=for-the-badge&logo=React Query&logoColor=white"> <img src="https://img.shields.io/badge/React Hook Form-EC5990?style=for-the-badge&logo=React&logoColor=white"> 

### BE
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">  <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white">  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">  

<img src="https://img.shields.io/badge/AmazonRDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"> <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">  

### Deploy
 <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">  <img src="https://img.shields.io/badge/AmazonS3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">  <img src="https://img.shields.io/badge/Vercel-000000?style=for-the-badge&logo=Vercel&logoColor=white"> 

### configuration management
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"> <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white">

## 📌 구현기능
### FE
- 커스텀 Carousel UI
- UI의 Image가 지연 로딩되는 것을 방지하는 Image PreLoad Chacing 전략
- Login/LogOut의 함수 컴포넌트의 경우 View를 제외한 로직을 최대한 분리
- Input validation에 대한 에러 실시간 렌더링
- 유저 & 게시글 CRUD 구현
- Cookie와 LocalStorage를 이용한 따른 Access Token 관리 
- 개발자도구를 이용해 Input Vadliation 변경을 막는 HTML과 JS단의 2중 Custom Validation
- React-query를 이용한 커스텀 Caching
- Token이 없는 유저의 특정 Route 접속을 막기 위한 useRouteSpy Hook
- 현재 Route에 따른 interative motion Navigation
- HashRoute를 이용한 Scroll Handling
- GlobalStyle을 이용한 DarkTheme/LightTheme과 전역적으로 사용되는 CSS값 소프트코딩

### BE
- 스프링 시큐리티를 이용한 JWT 로그인
- HTTPS(Vercel)과 HTTP(AWS EC2) 통신을 위한 SSL 적용 및 보안 강화
- 게시글 CRUD  
- S3를 이용한 이미지 저장기능  
- QueryDSL을 이용한 동적인 조건부 검색기능  
- 예외처리  
  - GlobalExceptionHandler로 controller의 예외를 전역적으로 처리  
  - ExceptionHandlerFilter를 추가하여 시큐리티에서 JWT검증간의 예외를 일괄적으로 처리  


## 🖼️  ERD  

<details>
<summary>ERD 확인하기</summary>
<div markdown="1">

<img width="584" alt="image" src="https://user-images.githubusercontent.com/118401337/222376315-fabfdd42-6801-4321-bae9-eebadd345d0f.png">

</div>
</details>

## 📃  API 명세서  
[Notion](https://www.notion.so/7a1a103555b74c8cbe12e3b12eba30ff?v=fb7bfa51c3d44dc8bb90403e3f4a8b3e&pvs=4)

## 📍  트러블 슈팅    
<details>
<summary>무한 리다이렉트</summary>
<div markdown="1">

  - 문제점
    - 로그인 시 POST요청을 보내는데 콘솔에서는 GET요청을 받은 것으로 인식됨
- 원인
    - 검색결과 포스트맨은 서버로부터 리다이렉트 요청이 오면 GET요청을 반환한다는 사실을 알게됨
    - 그래서 그 설정을 끄고 다시 실행해보니 요청이 무한루프에 돌면서 에러 발생
    - `http.formLogin().loginPage("/api/login").permitAll();`
    - 시큐리티에서 위의 코드로 로그인 페이지 설정을 해서 오류가 난 것으로 추측됨
        - 리다이렉트를 요청한것으로 추측
    - 위의 코드를 지워서 문제 해결

</div>
</details>

<details>
<summary>doFilter</summary>
<div markdown="1">

- 문제점
    - 요청을 보내도 요청이 컨트롤러에 도달하지 못함
- 원인
    - 커스텀 시큐리티 필터에서 doFiler(request, response) 메서드를 빼먹어서 컨트롤러까지 요청이 닿지 않은것으로 추정
    - doFilter메서드를 추가해서 문제 해결

</div>
</details>

<details>
<summary>S3 파일 삭제</summary>
<div markdown="1">

- 문제점
    - S3에서 한글로된 파일들만 삭제시 에러 발생
    - 영어로 이름을 변경하려 했으나 알수없는 에러 발생  
- 해결법
    - 브라우저를 사파리에서 크롬으로 변경하니 문제없이 삭제됨

</div>
</details>

<details>
<summary>SQLException</summary>
<div markdown="1">

- 오류명
    - **java.sql.SQLException: Field 'filename' doesn't have a default value**
- 문제점
    - 검색해보니 위 에러의 원인은 크게 두가지였다.
    - 필드에 디폴트 값을 지정하지 않은 경우, 기본키 생성 전략의 세팅 문제
    - 현재 테이블에 filename이라는 컬럼이 존재하지도 않고 id값을 제외하고는 NOT NULL도 없어서 첫번째 경우는 넘어갔다.
    - 기본키 전략을 Identity에서 auto로 변경해봤지만 아무런 변화가 없었다.
    - 혹시 저장하는 이미지의 파일명에 공백이 있어서 문제가 생기나 싶어서 변경해봤지만 변화가 없었다.
- 해결방법
    - DB를 다시 초기화하고 실행해보려고 ddl-auto를 update → create로 변경해서 실행했는데 오류가 해결되었다.
    - 확실치는 않지만 기존에 저장된 값의 기본키가 뒤섞이면서 새로운 데이터를 저장하려는데 기본키의 unique특성이 지켜지지 않아서 생긴 문제라고  추측했다.
    - filename이라는 필드명은 여전히 미지수

</div>
</details>
