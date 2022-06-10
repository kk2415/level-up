package com.levelup.api.intercepter;

import com.levelup.api.config.SessionName;
import com.levelup.core.exception.NotLoggedInException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckApiIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (!request.getMethod().equals("GET") &&
                (session == null || session.getAttribute(SessionName.SESSION_NAME) == null)) {
            throw new NotLoggedInException("미인증 요청입니다.");
        }
        return true;
    }

}
