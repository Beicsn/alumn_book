package com.gdpu.homework.Config;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gdpu.homework.Entity.Config.JsonData;
import com.gdpu.homework.Entity.Config.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

//token拦截器,验证token,实现 HandlerInterceptor接口
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
               //执行MVC相关的操作要验证token，否则放行
            if(!(handler instanceof HandlerMethod)){
                return  true;
            }
            //放行逻辑如下,有配置@DisableToken注解的放行
            DisableToken disableToken = ((HandlerMethod) handler).getMethodAnnotation(DisableToken.class);
            if(disableToken != null){
                //放行
                return  true;

            }
            //10010为未登录状态,10011为登录token失效
            //token为空,拦截操作返回错误信息
            String token = getToken(request);
            System.out.println(token);
            if(StringUtils.isEmpty(token)){

                response.getWriter().println(10010);
                return  false;
            }
            //有token但是是假的或者失效,返回状态码10011
            if(!TokenUtils.verify(token)){


                response.getWriter().println(10011);
                return  false;
            }

        return  true;



    }
    /*
    获取token，先从请求头中获取,若无，则从url参数中取值
     */
    private String getToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        return  token;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
