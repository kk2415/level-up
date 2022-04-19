package com.together.levelup.service;

import com.together.levelup.domain.member.Member;
import com.together.levelup.exception.MemberNotFoundException;
import com.together.levelup.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * return NULL이면 로그인 실패
     * */
    public Member login(String email, String password) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if (findMembers.size() > 0) {
            return findMembers.get(0);
        }
        throw new MemberNotFoundException(" 이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
    }

}
