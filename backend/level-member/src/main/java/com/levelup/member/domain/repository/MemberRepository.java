package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<Member> findById(Long id);

    @EntityGraph(attributePaths = {"roles"})
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}