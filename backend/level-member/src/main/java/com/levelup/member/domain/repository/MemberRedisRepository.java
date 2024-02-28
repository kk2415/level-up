package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRedisRepository {

    public void save(MemberEntity member) {
    }

    public String getKey(Long memberId) {
        return "MEMBER:" + memberId;
    }
}
