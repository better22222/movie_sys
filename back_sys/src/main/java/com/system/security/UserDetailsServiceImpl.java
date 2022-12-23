package com.system.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.User;
import com.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //不需要判断输入的用户名和密码对不对，只需要根据用户名username查询的用户详细信息即可
        //查询出来的详细详细，封装到UserDetails对象中，返回给SpringSecurity
        //自动完成用户名密码验证

        User user = userService.getOne(new QueryWrapper<User>().eq("username", s));
        if(user == null){
            //security 自带的无用户异常
            throw new UsernameNotFoundException("用户名不存在");
        }
        return new AccountUser(user.getId(),user.getUsername(),user.getPassword(),
                getUserAuthority(user.getId()));
    }

    public List<GrantedAuthority> getUserAuthority(Long userId){
        //通过sc内置工具栏，将查询出来的权限字符串转换为 GrantedAuthority 集合对象
        return AuthorityUtils.commaSeparatedStringToAuthorityList(userService.getUserAuthorityInfo(userId)) ;
    }
}
