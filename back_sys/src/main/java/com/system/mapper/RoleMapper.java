package com.system.mapper;

import com.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
public interface RoleMapper extends BaseMapper<Role> {
        public List<Role> listRoleByUserId(Long userId);

}
