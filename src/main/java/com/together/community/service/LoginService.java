package com.together.community.service;

import com.together.community.domain.member.Member;
import com.together.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * return NULL이면 로그인 실패
     * */
    public Member login(String loginId, String password) {
        List<Member> findMembers = memberRepository.findByLoginId(loginId);
        if (findMembers.size() > 0) {
            return findMembers.get(0);
        }
        return null;
    }

}
