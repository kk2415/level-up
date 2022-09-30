package com.levelup.api.filter;

import com.levelup.common.exception.ErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException
    {
        ErrorCode errorCode = ErrorCode.MEMBER_NOT_FOUND;

        if (exception instanceof InternalAuthenticationServiceException) {
            errorCode = ErrorCode.EMAIL_NOT_FOUND;
        } else if (exception instanceof BadCredentialsException) {
            errorCode = ErrorCode.PASSWORD_NOT_FOUND;
        }

        setDefaultFailureUrl("/api/v1/sign-in/failure?errorCode=" + errorCode.name());
        super.onAuthenticationFailure(request,response,exception);
    }
}
