package com.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.Result;
import com.system.entity.Menu;
import com.system.entity.RoleMenu;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController {
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result list(){
        List<Menu> menuList = menuService.tableTree();
        return Result.success(menuList);
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result info(@PathVariable Long id){
        Menu byId = menuService.getById(id);
        return Result.success(byId);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result save(@RequestBody Menu menu){
        menu.setCreated(LocalDateTime.now());
        menuService.save(menu);
        return Result.success("添加成功");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result update(@RequestBody Menu menu){
        menu.setUpdated(LocalDateTime.now());
        menuService.updateById(menu);
        //清除与该菜单相关的缓存信息
        userService.ClearUserAuthorityByMenuId(menu.getId());
        return Result.success("添修改成功");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result delete(@PathVariable Long id){
        //通过id查询删除的菜单有多少个子菜单
        int count = menuService.count(new QueryWrapper<Menu>()
                .eq("parent_id", id)
        );
        if(count > 0){
            return Result.fail("请先删除子菜单");
        }
        //清楚与该菜单关联的权限缓存
        userService.ClearUserAuthorityByMenuId(id);
        menuService.removeById(id);
        // 如果菜单删除，中间表sys_role_menu 数据也要同步删除
        roleMenuService.remove(new QueryWrapper<RoleMenu>()
            .eq("menu_id",id)
        );
        return Result.success("删除成功");
    }


}
