package com.levelup.core.repository.member;


import com.levelup.core.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    Long save(Member member);
    Member findById(Long id);
    Member findByEmail(String email);
    public Member findByEmailWithOutEmailAuth(String email);
    Member findByEmailAndPassword(String email, String password);
    List<Member> findByName(String name);
    List<Member> findByChannelId(Long channelId);
    List<Member> findByChannelId(Long channelId, int page, int count);
    List<Member> findAll();
    void delete(Long id);

}
