package com.system.common.exception;

import org.springframework.security.authentication.AuthenticationServiceException;



//验证码失败异常
public class CaptchaException extends AuthenticationServiceException {
    public CaptchaException(String message){
        super(message);
    }

}
