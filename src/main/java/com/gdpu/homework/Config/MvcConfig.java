package com.gdpu.homework.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
跨域配置和拦截器配置
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private  TokenRootInterceptor rootInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/vue/**")//配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径
                .allowedOrigins("*")//允许所有域名
                .allowedMethods("GET","POST","PUT","DELETE")//允许方法
                .allowCredentials(true) // 响应头表示是否可以将对请求的响应暴露给页面。
                .allowedHeaders("*");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加token校验拦截器,暂时先将除登录请求之外的都配置拦截,含有@DisableToken注解的操作则拦截器会放行
//        /excludePathPatterns("/vue/login/**");
    //.addPathPatterns("/vue/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/vue/**");
        registry.addInterceptor(rootInterceptor).addPathPatterns("/vue/**");
    }
}