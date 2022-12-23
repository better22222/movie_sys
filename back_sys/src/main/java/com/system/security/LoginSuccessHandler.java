package com.system.security;

import cn.hutool.json.JSONUtil;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.service.UserService;
import com.system.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//认证成功的处理器：登录成功后，返回一个登录成功信息到vue
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream out = response.getOutputStream();

        //获得用户名
        String name = authentication.getName();
        String jwt = jwtUtils.generateToken(name);

        //在响应头中设置返回的token
        response.setHeader(jwtUtils.getHeader(),jwt);

        //通过username获得当前用户的纤细纤细，需要在userService查询
        Result result = Result.success(null);

        //将result对象转为json发送至vue
        out.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        out.flush();
        out.close();
    }
}
