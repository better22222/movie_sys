package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.entity.Menu;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.mapper.UserMapper;
import com.system.service.MenuService;
import com.system.service.RoleService;
import com.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuService menuService;

    @Autowired
    RedisUtil  redisUtil;

    //获得当前登录用户的权限列表信息方法
    @Override
    public String getUserAuthorityInfo(Long userId) {
        //生成权限列表信息,登录时放入redis，做缓存
        //ROLE_admin，ROLE_normal, sys:manage, sys:user:list.....
        String authority="";
        User user = userMapper.selectById(userId);

        //判断redis内是否有数据
        if(redisUtil.hasKey(user.getUsername())){
            //key存在，表示已加载过
            authority =(String) redisUtil.get(user.getUsername());
            return authority;
        }

        roleService.list(new QueryWrapper<>());
        //获得用户角色对象
        List<Role> roles = roleService.listRolesByUserId(userId);

        if(roles.size() > 0){
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            //roleCodes就是角色权限编码ROLE_admin，ROLE_normal
            roleCodes = roleCodes.concat(",");
            authority = authority.concat(roleCodes);
            log.info("角色权限列表"+roleCodes);
        }

        //生成权限列表信息
        List<Long> menuIds = userMapper.getNavMenuIds(userId);
        if(menuIds.size() > 0){
            //根据菜单编号，查询菜单对象的详细详细
            List<Menu> menus = menuService.listByIds(menuIds);
            //将菜单详细信息拼接成权限字符串
            String menusPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            //将权限列表与角色列表连接
            authority = authority.concat(menusPerms);
            log.info("角色和权限列表"+ authority);
        }

        //将权限列表加入缓存

        //key为用户名，value是权限信息
        redisUtil.set(user.getUsername(),authority);
        return authority;
    }


    //当用户退出，或角色权限改变，清楚redis内权限缓存
    @Override
    public void ClearUserAuthority(String userName) {
        redisUtil.del(userName);
        log.info("删除权限信息成功");
    }

    @Override
    public void ClearUserAuthorityByMenuId(Long id) {
        //查询与该菜单关联的用户
        List<User> userListByMenuId = userMapper.getUserListByMenuId(id);
        userListByMenuId.forEach(u->{
            ClearUserAuthority(u.getUsername());
        });
    }

    //角色管理，根据角色编号RoleId，赋值了该角色的用户，需清空这些用户的权限缓存
    @Override
    public void ClearUserAuthorityByRoleId(Long id) {
        List<User> users = this.list(new QueryWrapper<User>()
                .inSql("id", "select user_id from sys_user_role where role_id =" + id)
        );
        users.forEach(u->{
            ClearUserAuthority(u.getUsername());
        });
    }



    @Override
    public User getUserByUserName(String username) {
        return this.getOne(new QueryWrapper<User>().eq("username", username));
    }
}
