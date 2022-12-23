package com.system.service;

import com.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
public interface RoleService extends IService<Role> {
    //根据用户id。获取该用户角色的对象信息
    public List<Role> listRolesByUserId(Long userId);
}
