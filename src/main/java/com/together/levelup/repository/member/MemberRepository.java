package com.together.levelup.repository.member;

import com.together.levelup.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findById(Long id);
    public Member findByEmail(String email);
    public Member findByEmailAndPassword(String email, String password);
    public List<Member> findByName(String name);
    public List<Member> findByChannelId(Long channelId);
    public List<Member> findByChannelId(Long channelId, int page, int count);
    public List<Member> findWaitingMemberByChannelId(Long id);
    public List<Member> findWaitingMemberByChannelId(Long id, int page, int count);
    public List<Member> findAll();
    public void delete(Long id);

}
