package com.together.levelup.repository.member;

import com.together.levelup.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public Member findById(Long id);
    public List<Member> findByName(String name);
    public List<Member> findByEmail(String email);
    public List<Member> findAll();
    public void delete(Long id);

}
