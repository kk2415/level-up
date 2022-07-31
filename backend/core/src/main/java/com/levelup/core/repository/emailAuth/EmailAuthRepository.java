package com.levelup.core.repository.emailAuth;

import com.levelup.core.domain.auth.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<List<EmailAuth>> findByEmail(String email);
    Optional<EmailAuth> findByMemberId(Long memberId);

}
