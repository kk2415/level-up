# Level Up

## :information_desk_person: Overview
Gradle 멀티 모듈을 활용한 게시판 프로젝트

### 성능 개선
+ JMeter를 활용한 성능테스트
쿼리 튜닝, 캐싱, 스케일아웃 & 로드밸런싱으로 성능 개선
검색 쿼리 개선
+ 기존 15초가 걸리던 쿼리 속도를 fulltext 인덱스 활용하여 70ms로 개선

### 주요 기능
+ :circus_tent: **스터디/프로젝트 모집**  
  + 간편하게 스터디와 프로젝트 모임을 개설하고 원하는 모임을 신청할 수 있어요
+ :clipboard: **QnA 게시판**  

#### 그 외 잡다한 기능
+ :+1: 좋아요 기능
+ :mag_right: 검색 기능


<br/>

## :hammer: Development Environment
### Backend
+ Java(OpenJDK 11)  
+ Spring Boot, Spring Data JPA, Spring Security (2.6.8)
+ Spring Cloud (3.0.3)
+ MySQL(8.0.29)  

### Frontend
+ HTML, CSS, JavaScript, React  

### Infra
+ AWS EC2, S3, RDS, ALB  
<br/><br/>


## Application Architecture

![aws 구조 (3)](https://user-images.githubusercontent.com/79124915/194025162-6d67c804-b8d5-4235-8de6-7fa30bbd4801.png)
