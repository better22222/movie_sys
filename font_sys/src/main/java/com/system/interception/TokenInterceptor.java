package com.system.interception;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.system.common.R;
import com.system.common.ResultCode;
import com.system.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    //如果vue的请求设置， 请求头变为跨域请求（复杂请求），正是请求之前发送嗅探请求，然后才发送正式请求

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        JwtUtils jwtUtils = new JwtUtils();
        ServletOutputStream outputStream = response.getOutputStream();

        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            response.setHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Headers","*");
            response.setHeader("Access-Control-Allow-Methods","*");
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Max-Age","3600");
        }

        String token = request.getHeader("token");
        log.info("token拦截器获得的值---"+token);

        if(token == null){
            outputStream.write(JSONUtil.toJsonStr
                    (R.error(ResultCode.TOKEN_NOT_EXIST,"token不存在，无权访问"))
                    .getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
            return false;
        }

        if(StringUtils.isNotBlank(token)){
            Claims claims = jwtUtils.getClaimByToken(token);
            if(claims == null){
                outputStream.write(JSONUtil.toJsonStr
                        (R.error(ResultCode.TOKEN_ERROR,"token错误，无权访问"))
                        .getBytes("utf-8"));
                outputStream.flush();
                outputStream.close();
                return false;
            }else{
                return true;
            }
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


}
