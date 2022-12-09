package com.levelup.member.domain.repository;

import com.levelup.member.domain.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(MemberEntity member) {
        redisTemplate.opsForValue().set(getKey(member.getId()), member);
    }

    public Optional<MemberEntity> findById(Long id) {
        MemberEntity member = (MemberEntity) redisTemplate.opsForValue().get(getKey(id));

        return Optional.ofNullable(member);
    }

    public String getKey(Long memberId) {
        return "MEMBER:" + memberId;
    }
}
