package com.levelup.api.filter;

import com.levelup.common.exception.AuthenticationErrorCode;
import com.levelup.common.web.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("from - {}, Responding with unauthorized error. Message - {}", request.getRequestURL(), e.getMessage());

        ExceptionResponse exceptionResponse = ExceptionResponse.from(AuthenticationErrorCode.FAIL_AUTHENTICATION);

        Object errorCode = request.getAttribute("AuthenticationErrorCode");
        if (errorCode != null) {
            exceptionResponse = ExceptionResponse.from((AuthenticationErrorCode) errorCode);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(exceptionResponse.getStatus());
        response.getWriter().write(exceptionResponse.toString());
    }
}
