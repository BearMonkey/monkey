package org.monkey.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录验证拦截器
 * @author chenc
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    
    private Logger log = Logger.getLogger(LoginInterceptor.class); 
    
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
        String url = request.getRequestURL().toString();
        log.info("url:" + url);
        return true;
    }
}