package com.system.mapper;

import com.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    //通过userId用户编号，查询该用户的权限菜单（返回菜单编号
    List<Long> getNavMenuIds(Long userId);

    //通过menuId查找与该菜单相关联的所有用户
    List<User> getUserListByMenuId(Long id);
}
