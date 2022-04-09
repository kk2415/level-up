package com.together.community.repository.member;

import com.together.community.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findById(Long id);
    public List<Member> findByName(String name);
    public List<Member> findByLoginId(String name);
    public List<Member> findAll();

}
