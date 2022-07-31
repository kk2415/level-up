package com.levelup.core.repository.member;

import com.levelup.core.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {

    Optional<Member> findByEmail(String email);
}
