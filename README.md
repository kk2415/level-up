# Overview
레벨업(level up) 프로젝트는 스터디 및 프로젝트 개설, 모객을 주목적으로 제작된 프로젝트입니다.
자신이 원하는 주제로 채널을 개설할 수 있으며 모객 활동을 할 수 있습니다.
사용자들은 등록된 채널 중에서 자신이 마음에 드는 채널에 가입 신청을 할 수 있습니다.
채널 매니저가 채널 가입을 승인하면 채널에서 활동이 가능합니다. 각 채널은 별도의 게시판 기능이
제공되어 채널원끼리 게시글 및 댓글로 소통이 가능합니다.

---

# Getting Started
+ Clone Project
  ```shell
    git clone https://github.com/kk2415/level-up.git
  ```
+ Configuration Files settings
    + enter required information in `level-api/src/main/resource/application.yml` and `level-image/src/main/resource/application.yml` file
      ```yaml
        spring:
          datasource:
            driver-class-name: com.mysql.cj.jdbc.Driver
            url: jdbc:mysql://localhost:3306/{database}?serverTimezone=Asia/Seoul
            username: {username}
            password: {password}
      ```
      ```yaml
        spring:
          redis:
            host: 
            port:
            password:
      ```
      ```yaml
        spring:
          mail:
            username: 
            password: 
      ```      
      ```yaml
        file:
          storage:
            dir: 
      ```      
      
    + execute DDL query\
      execute Table CREATE query in resource/schema.sql file
+ Infra Setting
  + Start mysql container and redis container
    ```shell
      docker-compose up -d
    ```
+ Build And Start
  ```shell
    ./gradlew :level-api:clean build
    ./gradlew :level-image:clean build
  ```
  ```shell
    java -jar .\level-api\build\libs\level-api-v1.0.jar --jasypt.encryptor.password={encryption_key}
    java -jar .\level-image\build\libs\level-image-v1.0.jar --jasypt.encryptor.password={encryption_key}
  ```

---

# Development Environment
+ Java(OpenJDK 11)  
+ Spring Boot, Spring Data JPA, Spring Security (2.6.8)
+ Spring Cloud (3.0.3)
+ MySQL(8.0.29)  

---

# Application Architecture

![aws 구조 (3)](https://user-images.githubusercontent.com/79124915/194025162-6d67c804-b8d5-4235-8de6-7fa30bbd4801.png)
