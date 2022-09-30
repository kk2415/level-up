package com.levelup.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.member.domain.service.dto.AccessToken;
import com.levelup.member.util.jwt.TokenProvider;
import com.levelup.member.domain.MemberPrincipal;
import com.levelup.api.controller.v1.dto.request.member.LogInMemberRequest;
import com.levelup.api.controller.v1.dto.response.member.LogInMemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final TokenProvider tokenProvider;

        @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
        {
        log.info("login filter start = url : {}", request.getRequestURL());

        try {
            LogInMemberRequest creds = new ObjectMapper().readValue(request.getInputStream(), LogInMemberRequest.class);

            //ProviderManager
            AuthenticationManager authenticationManager = getAuthenticationManager();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>(10)
                    )

            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException
    {
        log.info("login filter successfulAuthentication() start = url : {}", request.getRequestURL());

        MemberPrincipal principal = (MemberPrincipal) authResult.getPrincipal();
        String token = tokenProvider.createAccessToken(principal.getUsername());

        AccessToken accessToken
                = AccessToken.of(token, tokenProvider.getExpiration(token), tokenProvider.getIssuedAt(token));
        LogInMemberResponse loginResponse
                = LogInMemberResponse.of(principal.getId(), principal.getUsername(), accessToken, principal.isAdmin());

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}