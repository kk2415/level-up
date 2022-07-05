# level up application

## 프로젝트 소개
프로젝트 소개 :  
배우고 싶은 주제 관련 스터디를 모집하거나 프로젝트 팀원을 구할 수 있는 플랫폼  
개발관련 QnA 게시판을 통해 지식공유  

메인화면  

![홈](https://user-images.githubusercontent.com/79124915/176629944-92604c4e-8d3e-4b2c-aa1e-ce07aeb6640d.jpg)


## 프로젝트 실행환경  

### Backend
+ Java
+ Spring boot, Spring Cloud Config, Spring Data JPA,
+ Spring Security
+ MySQL
  
### Frontend
+ html, CSS, JavaScript
+ 리액트  

### DevOps
+ AWS EC2, S3, Route 53



## 도메인 모델 및 테이블 설계
![erd](https://user-images.githubusercontent.com/79124915/176631701-efa23be9-e6b8-40d0-8db7-d7ba4e79a28b.PNG)  

### 회원과 이메일 인증 테이블 관계
회원은 회원가입 시 하나의 인증코드를 발급받는다. 하나의 회원은 하나의 이메일 인증코드
를 가진다. 이메일 인증코드를 재발급하면 레코드를 추가하지 않고 인증코드 컬럼을 update
한다.
### 회원과 채널 테이블 관계
회원은 여러 채널에 가입할 수 있다. 또한 채널은 다수의 회원을 받을 수 있다.
회원과 채널의 다대다 관계를 채널 회원 테이블로 일대다, 다대일로 풀었다.
### 추천 테이블
추천 테이블의 기본키는 회원 기본키, 게시글-댓글 기본키, ENUM타입 3가지로 구성된 복합
키이다.
동일한 회원이 동일한 댓글 or 게시글에 추천시 기본키 중복에러가 나므로 중복 추천이 불
가능하다.
### 댓글과 회원, 게시글 테이블 관계
회원은 다수의 댓글을 작성할 수 있다. 게시글은 다수의 댓글을 가질 수 있다.
### 게시글과 채널 게시글 테이블 관계
게시글과 채널 게시글은 상속관계이다. 채널 게시글은 게시글의 공통 속성을 상속받는다.
### 파일과 게시글, 채널 테이블 관계
게시글과 채널은 다수의 파일을 저장할 수 있다. 파일 테이블은 파일의 저장경로와 파일 이
름을 기억한다.  

![db 스키마](https://user-images.githubusercontent.com/79124915/176631924-f09efb35-8649-4d05-9620-c4e5edf1d1bf.PNG)

