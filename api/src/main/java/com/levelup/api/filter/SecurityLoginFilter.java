package com.levelup.api.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import com.levelup.api.api.MemberApiController;
import com.levelup.api.security.TokenProvider;
import com.levelup.api.service.MemberService;
import com.levelup.core.domain.member.Member;
import com.levelup.core.dto.LoginRequest;
import com.levelup.core.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class SecurityLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("login filter start");

        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            System.out.println(creds.getEmail());
            System.out.println(creds.getPassword());
            return getAuthenticationManager().authenticate(
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
                                            Authentication authResult) throws IOException, ServletException {
        log.info("login filter successfulAuthentication() start");

        ObjectMapper mapper = new ObjectMapper();
        String email = ((User)authResult.getPrincipal()).getUsername();
        Member member = memberService.findByEmail(email);
        String token = tokenProvider.create(member);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .email(email)
                .build();


        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(loginResponse));
    }

}
