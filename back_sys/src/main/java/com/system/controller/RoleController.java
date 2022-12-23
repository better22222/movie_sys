package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Role;
import com.system.entity.RoleMenu;
import com.system.entity.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    //获得所有的角色信息
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result list(String name){ //name是搜索栏中内容，有name就进行模糊查询
        //查询分页数据
        Page page = getPage();
        Page<Role> roles = roleService.page(page,new QueryWrapper<Role>()
            .like(StrUtil.isNotBlank(name),"name",name)
        );
        return Result.success(roles);
    }

    //根据角色id获得角色信息
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result info(@PathVariable Long id){
        Role role = roleService.getById(id);
        //从关联表 sys_role_menu表内查询角色关联的信息
        List<RoleMenu> roleMenuIds = roleMenuService.list(new QueryWrapper<RoleMenu>()
                .eq("role_id", id)
        );

        //.stream() 把一个数据源转换为源
        //.map() 将映射每个元素转换到对应的结果
        List<Long> menuIds = roleMenuIds.stream()
                .map(r -> r.getMenuId()).collect(Collectors.toList());

        role.setMenuIds(menuIds);
        return Result.success(role);
    }

    //编辑角色
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result update(@RequestBody Role role){
        //设置角色更新时间
        role.setUpdated(LocalDateTime.now());
        roleService.updateById(role);
        return Result.success(role);
    }

    //新建角色
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result save(@RequestBody Role role){
        //设置角色更新时间
        role.setCreated(LocalDateTime.now());
        roleService.save(role);
        return Result.success(role);
    }

    //删除角色
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] roleIds){

        roleService.removeByIds(Arrays.asList(roleIds));
        //同步删除其他数据
        //删除sys_user_menu同步数据
        roleMenuService.remove(new QueryWrapper<RoleMenu>()
            .in("role_id",roleIds)
        );
        //删除sys_user_role同步数据
        userRoleService.remove(new QueryWrapper<UserRole>()
            .in("role_id",roleIds)
        );

        return Result.success("删除成功");
    }

    //分配权限：保存分配给角色的权限信息， 保存sys_role_menu
    //参数：保存角色编号 roleId 参数：权限菜单的编号 menuIds[]
    @PostMapping("/perm/{roleId}")
    @Transactional
    @PreAuthorize("hasAuthority('sys:role:perm')")
    public Result perm(@PathVariable Long roleId, @RequestBody Long[] menuIds){
        List<RoleMenu> roleMenusList = new ArrayList<>();
        //循环 将角色编号与对应的菜单编号封装成RoleMenu关联表对象
        Arrays.stream(menuIds).forEach(menuId ->{
            RoleMenu new_rm = new RoleMenu();
            new_rm.setMenuId(menuId);
            new_rm.setRoleId(roleId);
            roleMenusList.add(new_rm);
        });


        //如果从现有的权限中去掉权限： 列如 2，3，4去掉3，4
        //从sys_role_menu 删除需去掉的权限编号
        roleMenuService.remove(new QueryWrapper<RoleMenu>()
            .eq("role_id",roleId)
        );

        //批量保存
        roleMenuService.saveBatch(roleMenusList);


        //清空该角色相关用户缓存内的权限信息
        //根据roleId角色编号，删除该用户的redis权限缓存
        userService.ClearUserAuthorityByRoleId(roleId);
        return Result.success("获取成功");
    }

}
