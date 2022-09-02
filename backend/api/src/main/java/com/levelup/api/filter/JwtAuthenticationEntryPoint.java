package com.levelup.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 스프링 시큐리티 인증 실패시 실행되는 로직
* */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("from - {}, Responding with unauthorized error. Message - {}", request.getRequestURL(), e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
