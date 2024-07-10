package com.seso.audit_trail.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log the request details
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Origin: " + request.getHeader("Origin"));
        return true;
    }

}
