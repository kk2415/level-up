package com.together.levelup;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() throws InterruptedException {
        initService.initDb();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void initDb() throws InterruptedException {
//            Member manager1 = Member.createMember(Authority.ADMIN, "admin",
//                    passwordEncoder.encode("00000000"), "운영자", Gender.MALE, "19970927",
//                    "010-2354-9960", new UploadFile("내 이미지", "/images/member/AFF947XXQ-5554WSDQ12.png"));
//
//            em.persist(manager1);
        }
    }
}
