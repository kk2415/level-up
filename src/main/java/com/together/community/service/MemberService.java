package com.together.community.service;

import com.together.community.controller.dto.MemberJoinForm;
import com.together.community.domain.member.Member;
import com.together.community.exception.DuplicateEmailException;
import com.together.community.repository.member.MemberRepository;
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
        validationDuplicateMember(memberForm.getEmailId()); //중복 회원 검증
        memberRepository.save(memberForm.toEntity());
        return memberRepository.findByEmailId(memberForm.getEmailId()).get(0).getId();
    }

    @Transactional
    public Long join(Member member) {
        validationDuplicateMember(member.getEmailId()); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(String emailId) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        List<Member> findMembers = memberRepository.findByEmailId(emailId);

        if (findMembers.size() > 0) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }
    }

    /**
     * 멤버조회
     * */
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /**
     * 멤버 삭제
     * */
    public void deleteMember(Long memberId) {
        memberRepository.delete(memberId);
    }

}
