package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(Member member) {
        redisTemplate.opsForValue().set(getKey(member.getId()), member);
    }

    public Optional<Member> findById(Long id) {
        Member member = (Member) redisTemplate.opsForValue().get(getKey(id));

        return Optional.ofNullable(member);
    }

    public String getKey(Long memberId) {
        return "MEMBER:" + memberId;
    }
}
