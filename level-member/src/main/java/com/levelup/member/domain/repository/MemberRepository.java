package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<MemberEntity> findById(Long id);

    @EntityGraph(attributePaths = {"roles"})
    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByNickname(String nickname);
}