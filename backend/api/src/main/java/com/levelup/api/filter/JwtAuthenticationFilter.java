package com.levelup.api.filter;

import com.levelup.api.util.jwt.TokenProvider;
import com.levelup.core.domain.member.Member;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);

            // JWT 토큰이 있어야 컨텍스트홀더에 authenticationToken 저장
            if (token != null && !token.equalsIgnoreCase("null")) {
                //id 가져오기
                Long memberId = Long.valueOf(tokenProvider.getMemberId(token));
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberNotFoundException("멤버를 찾을 수 없습니다."));

                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getAuthority().name()));

                //인증완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(member, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch(Exception e) {
            log.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
