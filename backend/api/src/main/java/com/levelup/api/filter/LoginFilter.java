package com.levelup.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.util.jwt.TokenProvider;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.role.Role;
import com.levelup.core.domain.role.RoleName;
import com.levelup.core.dto.member.LoginRequest;
import com.levelup.core.dto.member.LoginResponse;
import com.levelup.core.exception.member.MemberNotFoundException;
import com.levelup.core.repository.member.MemberRepository;
import com.levelup.core.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("login filter start = url : {}", request.getRequestURL());

        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            //ProviderManager
            AuthenticationManager authenticationManager = getAuthenticationManager();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        log.info("login filter successfulAuthentication() start = url : {}", request.getRequestURL());

        ObjectMapper mapper = new ObjectMapper();

        String email = ((User)authResult.getPrincipal()).getUsername();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 이메일입니다."));

        Set<Role> roles = roleRepository.findByMemberId(member.getId());
        boolean isAdmin = roles.stream().anyMatch(role -> role.getRoleName().equals(RoleName.ADMIN));

        String token = tokenProvider.create(member);
        LoginResponse loginResponse = LoginResponse.of(member.getId(), email, token, isAdmin);

        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(loginResponse));
    }
}
