package com.system.config;

import com.system.interception.TokenInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class BootConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                //需要拦截路径
                .addPathPatterns("/**")
                //拦截白名单
                .excludePathPatterns("/","/fans/login","/fans/register");
    }
}
