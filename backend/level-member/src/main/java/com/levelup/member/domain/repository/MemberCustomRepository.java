package com.levelup.member.domain.repository;


import com.levelup.member.domain.entity.MemberEntity;

import java.util.Optional;

public interface MemberCustomRepository {
    Optional<MemberEntity> findByEmail(String email);
}
