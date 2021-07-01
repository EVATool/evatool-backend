package com.evatool.application.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    // TODO The preHandle method get executed AFTER many auth requests have been processed.
    //  It should fire BEFORE they are processed in order to inject the realm name into the headers.
    //  This is currently not used but might be if the Realm header is removed and the realm is figured out automatically.

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("INTERCEPTOR - PRE HANDLE");
        //System.out.println(request.getHeader("Realm"));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("INTERCEPTOR - POST HANDLE");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        System.out.println("INTERCEPTOR - AFTER COMPLETION");
    }
}
