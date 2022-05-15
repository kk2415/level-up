package com.together.levelup.service;

import com.together.levelup.dto.member.MemberJoinForm;
import com.together.levelup.domain.member.Member;
import com.together.levelup.exception.DuplicateEmailException;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    final int HANGUL_UNICODE_START = 0xAC00;
    final int HANGUL_UNICODE_END = 0xD7AF;

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * */
    @Transactional
    public Long join(MemberJoinForm memberForm) {
        validationDuplicateMember(memberForm.getEmail()); //중복 회원 검증
        memberRepository.save(memberForm.toEntity());
        return memberRepository.findByEmail(memberForm.getEmail()).getId();
    }

    @Transactional
    public Long join(Member member) {
        validationDuplicateMember(member.getEmail()); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(String email) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        Member findMembers = memberRepository.findByEmail(email);

        System.out.println(email);
        if (findMembers != null) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }
    }

    /**
     * 멤버조회
     * */
    public Member findRegisterMember(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member findByEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new MemberNotFoundException("가입된 이메일이 없습니다");
        }
        return member;
    }

    public List<Member> findByChannelId(Long channelId) {
        return memberRepository.findByChannelId(channelId);
    }

    public List<Member> findByChannelId(Long channelId, Long page, Long count) {
        return memberRepository.findByChannelId(channelId, Math.toIntExact(page), Math.toIntExact(count));
    }

    public List<Member> findWaitingMemberByChannelId(Long channelId) {
        return memberRepository.findWaitingMemberByChannelId(channelId);
    }

    public List<Member> findWaitingMemberByChannelId(Long channelId, Long page) {
        return memberRepository.findWaitingMemberByChannelId(channelId, Math.toIntExact(page), 5);
    }

    /**
     * 멤버 삭제
     * */
    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }

}
