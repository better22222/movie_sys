package com.system.service;

import com.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
public interface UserService extends IService<User> {
        //获得当前登录用户的权限列表信息方法
        public String getUserAuthorityInfo(Long userId);

        //当用户退出，清除redis内权限缓存
        public void ClearUserAuthority(String userName);
        //根据菜单编号，清除与菜单关联的用户缓存
        public void ClearUserAuthorityByMenuId(Long id);
        //根据角色编号，清除权限缓存
        public void ClearUserAuthorityByRoleId(Long id);

        //通过用户名查询用户信息
        public User getUserByUserName(String username);
}
