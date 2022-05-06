package com.together.levelup.intercepter;

import com.together.levelup.api.SessionName;
import com.together.levelup.domain.member.Authority;
import com.together.levelup.domain.member.Member;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("isAdmin", false);

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionName.SESSION_NAME) != null) {
            Member member = (Member) session.getAttribute(SessionName.SESSION_NAME);

            if (member.getAuthority() == Authority.ADMIN) {
                request.setAttribute("isAdmin", true);
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        ModelAndView mv = new ModelAndView();

        mv.addObject("isAdmin", false);
        if (request.getAttribute("isAdmin").equals(true)) {
            mv.addObject("isAdmin", true);
        }

        modelAndView = mv;
    }

}
