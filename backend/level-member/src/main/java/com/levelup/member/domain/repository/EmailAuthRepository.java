package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    @Query(value = "select * from email_auth ea " +
            "join member m on m.member_id = ea.member_id " +
            "where m.email = :email and ea.email_auth_type = :authType " +
            "order by ea.email_auth_id desc limit 1", nativeQuery = true)
    Optional<EmailAuth> findByEmailAndAuthType(@Param("email") String email, @Param("authType") String authType);

    List<EmailAuth> findByEmail(String email);
}
