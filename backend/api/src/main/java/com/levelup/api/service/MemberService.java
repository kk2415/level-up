package com.levelup.api.service;


import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.member.MemberJoinForm;
import com.levelup.core.exception.DuplicateEmailException;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

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

    /**
     * 로그인 처리
     * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername start");

        Member member = memberRepository.findByEmail(username);
        if (member == null) {
            throw new MemberNotFoundException("잘못된 이메일입니다.");
        }

        return new User(member.getEmail(), member.getPassword(), true, true,
                true, true, new ArrayList<>());
    }
}
