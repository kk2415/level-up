package com.together.community.service;

import com.together.community.domain.member.Member;
import com.together.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * */
    @Transactional
    public Long join(Member member) {
        validationDuplicateMember(member); //회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validationDuplicateMember(Member member) {
        //이 로직은 동시성 문제가 있음. 동시에 같은 아이디가 접근해서 호출하면 통과될 수 있음. 차후에 개선
        List<Member> findMembers = memberRepository.findByLoginId(member.getLoginId());

        if (findMembers.size() > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
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

}
