package com.example.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by suman.das on 7/23/19.
 */
@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();



        //String tenantID = request.getHeader("X-TenantID");
        System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: ");
        System.out.println("____________________________________________");

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        System.out.println("__________________Post Handle__________________________");

    }

}
