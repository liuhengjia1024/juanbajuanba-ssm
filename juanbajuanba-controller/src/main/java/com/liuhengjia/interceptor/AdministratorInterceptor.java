package com.liuhengjia.interceptor;

import com.liuhengjia.constant.Power;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdministratorInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return request.getSession().getAttribute(Power.ADMINISTRATOR.getKey()) != null;
    }
}
