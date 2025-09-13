# 🚀 Level-Up

> 스터디 및 프로젝트 개설, 모객을 위한 커뮤니티 플랫폼

레벨업(Level-Up)은 사용자들이 원하는 주제로 채널을 개설하고, 관심 있는 채널에 가입하여 함께 학습하고 성장할 수 있는 커뮤니티 플랫폼입니다.

## 📋 목차

- [주요 기능](#-주요-기능)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)
- [API 문서](#-api-문서)
- [아키텍처](#-아키텍처)
- [개발 환경](#-개발-환경)

## ✨ 주요 기능

### 🏢 채널 관리
- **채널 개설**: 원하는 주제로 스터디/프로젝트 채널 생성
- **채널 가입**: 관심 있는 채널에 가입 신청
- **채널 승인**: 채널 매니저가 가입 신청 승인/거부
- **채널 활동**: 채널원들의 활동 점수 관리

### 📝 게시판 시스템
- **게시글 작성**: 채널 내에서 자유로운 게시글 작성
- **댓글 시스템**: 게시글에 댓글 및 대댓글 작성
- **투표 기능**: 게시글 및 댓글에 대한 투표
- **검색 및 페이징**: 효율적인 게시글 탐색

### 👥 회원 관리
- **회원가입/로그인**: OAuth2 기반 인증 시스템
- **이메일 인증**: 회원가입 시 이메일 인증
- **프로필 관리**: 개인정보 및 스킬 관리
- **JWT 토큰**: 안전한 인증 및 인가

### 🔔 알림 시스템
- **실시간 알림**: 채널 가입 승인, 댓글 등 실시간 알림
- **이메일 알림**: 중요한 활동에 대한 이메일 알림
- **Firebase**: 푸시 알림 지원

### 📁 파일 관리
- **이미지 업로드**: AWS S3 기반 이미지 저장
- **파일 관리**: 효율적인 파일 업로드 및 관리

## 🛠 기술 스택

### Backend
- **Java 11** - 프로그래밍 언어
- **Spring Boot 2.6.8** - 애플리케이션 프레임워크
- **Spring Security** - 인증 및 인가
- **Spring Data JPA** - 데이터 접근 계층
- **Spring Cloud** - 마이크로서비스 아키텍처
- **MySQL 8.0** - 관계형 데이터베이스
- **Redis** - 캐시 및 세션 저장소
- **JWT** - 토큰 기반 인증

### Infrastructure
- **Docker** - 컨테이너화
- **AWS S3** - 파일 저장소
- **Gradle** - 빌드 도구
- **Jasypt** - 설정 암호화

### Development Tools
- **SpringDoc OpenAPI** - API 문서화
- **Asciidoctor** - 문서 생성
- **Lombok** - 코드 간소화
- **Log4j2** - 로깅

## 🏗 프로젝트 구조

```
level-up/
├── level-api/           # 메인 API 서버
├── level-channel/       # 채널 관리 서비스
├── level-article/       # 게시글 관리 서비스
├── level-member/        # 회원 관리 서비스
├── level-notification/  # 알림 서비스
├── level-image/         # 이미지 관리 서비스
├── level-event/         # 이벤트 처리 서비스
├── level-common/        # 공통 모듈
├── docker-compose.yml   # 인프라 설정
└── build.gradle         # 빌드 설정
```

### 모듈별 역할

| 모듈 | 역할 | 주요 기능 |
|------|------|-----------|
| **level-api** | 메인 API 서버 | REST API 엔드포인트, 인증, 통합 |
| **level-channel** | 채널 관리 | 채널 CRUD, 가입 관리, 활동 점수 |
| **level-article** | 게시글 관리 | 게시글, 댓글, 투표 관리 |
| **level-member** | 회원 관리 | 회원가입, 로그인, 프로필 관리 |
| **level-notification** | 알림 서비스 | 실시간 알림, 이메일 알림 |
| **level-image** | 이미지 관리 | 파일 업로드, S3 연동 |
| **level-event** | 이벤트 처리 | 비동기 이벤트 처리 |
| **level-common** | 공통 모듈 | 공통 유틸리티, 예외 처리 |

## 🚀 시작하기

### 1. 프로젝트 클론

```bash
git clone https://github.com/kk2415/level-up.git
cd level-up
```

### 2. 환경 설정

#### 데이터베이스 설정
`level-api/src/main/resources/application.yml` 파일을 수정하세요:

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/level_up?serverTimezone=Asia/Seoul
    username: your_username
    password: your_password
```

#### Redis 설정
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
```

#### 이메일 설정
```yaml
spring:
  mail:
    username: your_email@gmail.com
    password: your_app_password
```

#### 파일 저장소 설정
```yaml
file:
  storage:
    dir: /path/to/upload/directory
```

### 3. 데이터베이스 스키마 생성

DDL 쿼리를 실행하여 데이터베이스 테이블을 생성하세요:
```sql
-- resource/schema.sql 파일의 CREATE TABLE 쿼리들을 실행
```

### 4. 인프라 실행

Docker Compose를 사용하여 MySQL과 Redis를 실행합니다:

```bash
docker-compose up -d
```

### 5. 애플리케이션 빌드 및 실행

```bash
# 프로젝트 빌드
./gradlew :level-api:clean build
./gradlew :level-image:clean build

# 애플리케이션 실행
java -jar ./level-api/build/libs/level-api-v1.0.jar --jasypt.encryptor.password=your_encryption_key
java -jar ./level-image/build/libs/level-image-v1.0.jar --jasypt.encryptor.password=your_encryption_key
```

## 📚 API 문서

애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### 주요 API 엔드포인트

#### 회원 관리
- `POST /api/v1/members/signup` - 회원가입
- `POST /api/v1/members/signin` - 로그인
- `POST /api/v1/members/email-auth` - 이메일 인증
- `GET /api/v1/members/profile` - 프로필 조회

#### 채널 관리
- `POST /api/v1/channels` - 채널 생성
- `GET /api/v1/channels` - 채널 목록 조회
- `POST /api/v1/channels/{channelId}/join` - 채널 가입 신청
- `POST /api/v1/channels/{channelId}/approve` - 가입 승인

#### 게시글 관리
- `POST /api/v1/articles` - 게시글 작성
- `GET /api/v1/articles` - 게시글 목록 조회
- `POST /api/v1/articles/{articleId}/comments` - 댓글 작성
- `POST /api/v1/articles/{articleId}/votes` - 투표

## 🏛 아키텍처

![아키텍처 다이어그램](https://user-images.githubusercontent.com/79124915/194025162-6d67c804-b8d5-4235-8de6-7fa30bbd4801.png)

### 마이크로서비스 아키텍처
- **API Gateway**: level-api가 모든 요청을 받아 적절한 서비스로 라우팅
- **서비스 분리**: 각 도메인별로 독립적인 서비스 운영
- **공통 모듈**: level-common을 통한 코드 재사용성 향상

### 데이터 흐름
1. 클라이언트 요청 → API Gateway (level-api)
2. 인증/인가 처리
3. 적절한 서비스로 요청 라우팅
4. 데이터베이스 조회/수정
5. 응답 반환

## 💻 개발 환경

### 필수 요구사항
- **Java 11** 이상
- **Docker** 및 **Docker Compose**
- **MySQL 8.0** 이상
- **Redis 6.0** 이상

### IDE 설정
- **IntelliJ IDEA** 권장
- **Lombok** 플러그인 설치
- **Gradle** 지원

### 개발 가이드라인
- **코드 스타일**: Google Java Style Guide 준수
- **커밋 메시지**: Conventional Commits 형식 사용
- **브랜치 전략**: Git Flow 사용
- **테스트**: 단위 테스트 및 통합 테스트 작성