package com.system.config;

import com.system.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//SpringSecurity 核心配置类， 所有定义过滤器、处理器都是在这里配置执行
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //方法级别的权限认证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //创建用于加密密码的对象
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //声明需要配置的过滤器和处理器对象
    @Autowired
    CaptchaFilter captchaFilter;
    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler; //权限不够异常处理器

    @Autowired
    LogoutSuccessSystemHandler logoutSuccessSystemHandler;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JwtAuthenticationFilter(authenticationManager());
    }


    //白名单
    public static final String[] URL_WHITE_LIST = {
            "/login",
            "/logout",
            "/captcha",
            "/upload/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //关闭跨域与csrf防护
        http.cors().and().csrf().disable()
                //登录配置
                .formLogin()
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)

                //登出操作
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessSystemHandler)


                //配置拦截规则
                .and()
                .authorizeRequests() //对所有的请求URL进行权限验证
                .antMatchers(URL_WHITE_LIST) //白名单
                .permitAll() //对所有人采用这些规则
                .anyRequest() //表示匹配任意的url请求
                .authenticated() //表示所有匹配到的url需要被认证才能访问

                //禁用session
                /*
                SessionCreationPolicy.STATELESS ----- 永远不会创建session对象
                SessionCreationPolicy.ALWAYS ----- 总是创建session对象
                SessionCreationPolicy.IF_REQUIRED ----- 只会在需要的时候创建session对象
                 */
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //配置异常的处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //sc会捕获authenticationEntryPoint异常，并使用对应的处理器处理
                .accessDeniedHandler(jwtAccessDeniedHandler) //权限不足时的异常


                //配置自定义过滤器
                .and()
                .addFilter(jwtAuthenticationFilter()) //配置jwt验证过滤器
                //在登录之前执行验证码过滤器
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);

    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}


