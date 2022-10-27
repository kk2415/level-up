package com.levelup.member.domain.service;

import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.member.domain.entity.Member;
import com.levelup.member.domain.entity.MemberPrincipal;
import com.levelup.member.domain.entity.Role;
import com.levelup.member.domain.entity.RoleName;
import com.levelup.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SignInService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("start loadUserByUsername");

        final Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Collection<GrantedAuthority> authorities = new ArrayList<>(10);
        List<Role> roles = member.getRoles();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().getName())));
        boolean isAdmin = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(RoleName.ADMIN.getName()));

        return new MemberPrincipal(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getPassword(),
                isAdmin,
                true,
                true,
                true,
                true,
                authorities);
    }
}
