package com.levelup.api.service;


import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * return NULL이면 로그인 실패
     * */
    public Member login(String email, String password, final PasswordEncoder encoder) {
        final Member findMember = memberRepository.findByEmail(email);

        if (findMember == null || encoder.matches(password, findMember.getPassword())) {
            throw new MemberNotFoundException(" 이메일 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
        }

        return findMember;
    }

}
