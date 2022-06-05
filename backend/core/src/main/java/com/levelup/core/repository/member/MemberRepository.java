package com.levelup.core.repository.member;


import com.levelup.core.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    void save(Member member);
    Member findById(Long id);
    Member findByEmail(String email);
    Member findByEmailAndPassword(String email, String password);
    List<Member> findByName(String name);
    List<Member> findByChannelId(Long channelId);
    List<Member> findByChannelId(Long channelId, int page, int count);
    List<Member> findWaitingMemberByChannelId(Long id);
    List<Member> findWaitingMemberByChannelId(Long id, int page, int count);
    List<Member> findAll();
    void delete(Long id);

}
