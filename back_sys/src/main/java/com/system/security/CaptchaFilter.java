package com.system.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.system.common.exception.CaptchaException;
import com.system.common.lang.Const;
import com.system.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
//在下一次之前，只执行一次的过滤器
public class CaptchaFilter extends OncePerRequestFilter {

    //定义一个路径
    private final String loginURL = "/login";

    @Autowired
    RedisUtil redisUtil;

    //声明一个登录失败的处理器
    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("验证码拦截器");
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        System.out.println(request.getMethod());
        if(requestURI.equals(loginURL) && request.getMethod().equals("POST")){
            log.info("获取到login请求成功，正在校验验证码---"+requestURI);
            try {
                validate(request);
            } catch (CaptchaException e) {
                log.info(e.getMessage());
                //TODO执行登录失败处理器LoginFailureHandLer(删除登录失败的信息到vue）
                loginFailureHandler.onAuthenticationFailure(request,response,e);
            }
        }
        //跳转至后面的处理器
        filterChain.doFilter(request,response);
    }

    //验证验证码方法
    private void validate(HttpServletRequest request) throws CaptchaException {
        String key = request.getParameter("key");
        String code = request.getParameter("captchaCode");

        System.out.println(key);
        System.out.println(code);

            //判断验证码是否为空
            if(StringUtils.isBlank(key) || StringUtils.isBlank(code)){
                throw new CaptchaException("验证码不能为空");
            }

            //从redis内获取验证码code并判断
            if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){
                throw new CaptchaException("验证码不正确");
            }
            log.info("验证码正确");

            log.info(key);
            //确保redis中的验证码是一次性的,每次验证之后删除code
            redisUtil.hdel(Const.CAPTCHA_KEY,key);





    }
}
