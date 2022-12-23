package com.system.security;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//无权限处理器
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("token认证失败，未登录");
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); //响应的状态403
        ServletOutputStream out = response.getOutputStream();


        log.info("系统操作权限不足");
        Result result = Result.fail("权限验证异常"+e.getMessage());

        //将result对象转为json发送至vue
        out.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        out.flush();
        out.close();
    }
}
