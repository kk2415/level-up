package com.levelup.api.filter;

import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ErrorCode;
import com.levelup.member.domain.entity.MemberEntity;
import com.levelup.common.exception.AuthenticationErrorCode;
import com.levelup.member.util.jwt.TokenProvider;
import com.levelup.member.domain.entity.MemberPrincipal;
import com.levelup.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        parseBearerHeader(request).ifPresentOrElse((token) -> {
            AuthenticationErrorCode validationResult = tokenProvider.validateToken(token);
            SecurityContext securityContext = SecurityContextHolder.getContext();

            if (validationResult.isValid() && securityContext.getAuthentication() == null) {
                MemberEntity member = memberRepository.findByEmail(tokenProvider.getSubject(token))
                        .orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
                MemberPrincipal userDetails = MemberPrincipal.from(member);

                AbstractAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                securityContext.setAuthentication(authenticationToken);
            } else if (!validationResult.isValid()) {
                request.setAttribute("AuthenticationErrorCode", validationResult);
            }
        }, () -> request.setAttribute("AuthenticationErrorCode", AuthenticationErrorCode.NULL_BEARER_HEADER));

        filterChain.doFilter(request, response);
    }

    private Optional<String> parseBearerHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = null;

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return Optional.ofNullable(token);
    }
}
