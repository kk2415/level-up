package com.levelup.api.filter;

import com.levelup.api.util.jwt.JwtException;
import com.levelup.api.util.jwt.TokenProvider;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("=======Start Jwt Authentication Filter=======");
        parseBearerToken(request)
                .ifPresent((token) -> {
                    JwtException validationResult = tokenProvider.validateToken(token);

                    switch (validationResult) {
                        case SUCCESS:
                            Long memberId = Long.valueOf(tokenProvider.getSubject(token));
                            Member member = memberRepository.findById(memberId)
                                    .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

                            Collection<GrantedAuthority> authorities = new ArrayList<>(10);
                            List<Role> roles = member.getRoles();
                            for (Role role : roles) {
                                authorities.add(new SimpleGrantedAuthority(role.getRoleName().getName()));
                            }

                            AbstractAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(member, null, authorities);

                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            break;
                        case EXPIRED:
                            log.error("Token's expired");
                            break;
                        default:
                            log.error("Token isn't valid");
                    }
                });
        filterChain.doFilter(request, response);
    }

    private Optional<String> parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = null;

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return Optional.ofNullable(token);
    }
}
