package com.system.security;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import com.system.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
主要请求地址内包含 /logout， sc会自动执行退出操作（清空登录时封装的用户信息对象authentication，并执行次方法
* */
//登出成功
@Component
@Slf4j
public class LogoutSuccessSystemHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //检测用户信息是否已被清空，未被清空手动清空
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }

        log.info("登出成功");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(jwtUtils.getHeader(),""); //响应一个空的token

        ServletOutputStream out = response.getOutputStream();

        Result result = Result.success("登出成功");

        //将result对象转为json发送至vue
        out.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        out.flush();
        out.close();
    }
}
