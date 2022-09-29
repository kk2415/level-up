package com.levelup.member.domain.repository;


import com.levelup.member.domain.entity.Member;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<Member> findByEmail(String email);
}
