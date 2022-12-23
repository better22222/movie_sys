package com.system.controller;

import cn.hutool.core.map.MapUtil;
import com.system.common.Result;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserService userService;




    @RequestMapping("/test")
    public Object test(){
        return Result.success( userService.list());
    }



}
