package com.levelup.member.domain.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class MemberPrincipal extends User {

    private final Long id;
    private final String nickname;
    private final boolean isAdmin;

    public MemberPrincipal(
            Long id,
            String nickname,
            boolean isAdmin,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities)
    {
        super(username, password, authorities);
        this.id = id;
        this.nickname = nickname;
        this.isAdmin = isAdmin;
    }

    public MemberPrincipal(
            Long id,
            String nickname,
            String username,
            String password,
            boolean isAdmin,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.nickname = nickname;
        this.isAdmin = isAdmin;
    }

    public static MemberPrincipal from(Member member) {
        return new MemberPrincipal(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.getPassword(),
                member.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN)),
                true,
                true,
                true,
                true,
                member.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().getName()))
                        .collect(Collectors.toUnmodifiableList())
        );
    }
}
