package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Role;
import com.system.entity.User;
import com.system.entity.UserRole;
import com.system.mapper.RoleMapper;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
@RequestMapping("/system/user")
public class UserController extends BaseController {

        @Autowired
        private RoleMapper roleMapper;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @PostMapping("/changePsw/{psw}")
        public Result changeP(@PathVariable String psw){
            return Result.success("ok");
        }

        @GetMapping("/list")
        public Result list(String name){
            Page<User> users = userService.page(getPage(), new QueryWrapper<User>()
                    .like(StrUtil.isNotBlank(name), "username", name)
            );
            //查询该用户所具备的角色对象信息
            users.getRecords().forEach(u ->{
                //查询每个用户对应的角色信息
                u.setRoles(roleMapper.listRoleByUserId(u.getId()));
            });
            return Result.success(users);
        }

        //通过id 获得该用户的信息,包括用户具有的角色
        @GetMapping("/info/{id}")
        public Result info(@PathVariable Long id){
            User user = userService.getById(id);
            List<Role> roles = roleService.listRolesByUserId(user.getId());
            user.setRoles(roles);
            return Result.success(user);
        }

        //保存用户
        @PostMapping("/save")
        @PreAuthorize("hasAuthority('sys:user:save')")
        public Result save(@RequestBody User user){
            //设置保存用户时间
            user.setCreated(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode( Const.DEFAULT_PASSWORD));
            user.setAvatar(Const.DEFAULT_AVATAR);
            userService.save(user);
            return Result.success(user);
        }

        //更新用户
        @PostMapping("/update")
        @PreAuthorize("hasAuthority('sys:user:update')")
        public Result update(@RequestBody User user){
            user.setUpdated(LocalDateTime.now());
            userService.updateById(user);
            return Result.success(user);
        }

        //删除用户
        @PostMapping("/delete")
        @Transactional
        @PreAuthorize("hasAuthority('sys:user:delete')")
        public Result delete(@RequestBody Long[] ids){
            userService.removeByIds(Arrays.asList(ids));
            //同步删除相关数据 送sys_user_role 关联表内取代哦与该用户关联的数据
            userRoleService.remove(new QueryWrapper<UserRole>()
                .in("user_id",ids)
            );
            return Result.success("");
        }

        //分配权限
        @PostMapping("/role/{userId}")
        @Transactional
        @PreAuthorize("hasAuthority('sys:user:role')")
        public Result rolePerm(@PathVariable Long userId, @RequestBody Long[] roleIds){
            //创建一个存储sys_user_role 关联表的数据对象集合
            List<UserRole> userRoleList = new ArrayList<>();
            Arrays.stream(roleIds).forEach(r ->{
                UserRole userRole = new UserRole();
                userRole.setRoleId(r);
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            });

            //从关联表中清空与该用户相关的所有角色
            userRoleService.remove(new QueryWrapper<UserRole>()
                .eq("user_id",userId)
            );

            //删除缓存
            User byId = userService.getById(userId);
            userService.ClearUserAuthority(byId.getUsername());
            //按照上面userRoleList重新录入进去
            userRoleService.saveBatch(userRoleList);
            return Result.success("");
        }

        //重置密码
        @PostMapping("/repass/{id}")
        public Result repass(@PathVariable Long id){
            User byId = userService.getById(id);
            byId.setPassword(passwordEncoder.encode(Const.DEFAULT_PASSWORD));
            byId.setUpdated(LocalDateTime.now());
            userService.updateById(byId);
            return Result.success("");
        }
}
