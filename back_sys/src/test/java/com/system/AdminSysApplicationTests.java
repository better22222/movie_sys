package com.system;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.mapper.RoleMapper;
import com.system.mapper.UserMapper;
import com.system.service.FansService;
import com.system.service.RoleService;
import com.system.service.UserService;
import com.system.util.JwtUtils;
import com.system.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RestController
class AdminSysApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    FansService fansService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;

    @Test
    void queli(){
        List<Role> roles = roleService.list(new QueryWrapper<Role>().inSql("id",
                "select role_id from sys_user_role where user_id=" + 1));
        System.out.println(roles);
    }

    @Test
    void queli1(){
        System.out.println(fansService.getById(1));
    }


    @RequestMapping("/test")
    public Object test(){
        return userService.list();
    }





    @Test
    public void updateUser(){
        User user = new User();
        user.setId(3L);
        user.setUsername("root");
        user.setPassword("123WE");
        user.setAvatar("");
        user.setStatu(1);
        userMapper.update(user,new UpdateWrapper<User>().eq("id",user.getId()));
    }

    @Test
    public void getUser() {
        File userBehaviorFile = new File("D:/user_behavior.log");
        try {
            userBehaviorFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void geneartion(){
        User one = userService.getOne(new QueryWrapper<User>().eq("username", "admin"));
        System.out.println(one);
    }

    @Test
    public void passEncode(){
        String pwd = "123";
        //加密后的密码
        String encode = bCryptPasswordEncoder.encode(pwd);
        System.out.println(encode);

        //密码验证
        boolean b = bCryptPasswordEncoder.matches(pwd, encode);
        System.out.println(b);
    }

    @Test
    public void  delete(){
        boolean equals = "8g73n".equals(redisUtil.hget(Const.CAPTCHA_KEY, "4f532a57-a452-4986-85ec-ddf8a3383211"));
        System.out.println(equals);

    }


}
