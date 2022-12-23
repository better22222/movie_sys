package com.system.service.impl;

import com.system.entity.Menu;
import com.system.entity.User;
import com.system.entity.dto.MenuDto;
import com.system.mapper.MenuMapper;
import com.system.mapper.UserMapper;
import com.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2022-11-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;




    @Override
    public List<MenuDto> getCurrentUserNav(String username) {
        User user = userService.getUserByUserName(username);
        //通过用户id获得该用户的菜单
        List<Long> navMenuIds = userMapper.getNavMenuIds(user.getId());
        //获得该用户能够操作的菜单id，通过id查询所有的菜单信息
        List<Menu> menus = this.listByIds(navMenuIds);
        //将菜单信息转换为Tree结构
        List<Menu> new_menu = this.buildChildrenMenu(menus);
        return this.buildTreeMenu(new_menu);
    }

    @Override
    public List<Menu> tableTree() {
        List<Menu> mentList = this.list();

        return buildChildrenMenu(mentList);
    }

    //构建MenuDto菜单
    public List<MenuDto> buildTreeMenu(List<Menu> menus){
        List<MenuDto> menuTree = new ArrayList<>();
        menus.forEach(m->{
            //创建一个新的菜单对象
            MenuDto temp = new MenuDto();
            temp.setId(m.getId());
            temp.setName(m.getPerms());
            temp.setTitle(m.getName());
            temp.setComponent(m.getComponent());
            temp.setPath(m.getPath());
            temp.setIcon(m.getIcon());

            if(m.getChildren().size() > 0){
                //使用递归，将子菜单转换为menuDto
                temp.setChildren(buildTreeMenu(m.getChildren()));
            }
            menuTree.add(temp);
        });
        return menuTree;
    }


    //构建菜单的子菜单信息
    public List<Menu> buildChildrenMenu(List<Menu> menus){
        List<Menu> new_menu = new ArrayList<>();
        //循环菜单的集合，查找每个菜单下的子菜单
        for(Menu m : menus){
            for(Menu e: menus){
                //判断m 菜单的id 是对于e菜单的父id，声明e是m的子菜单
                if(m.getId() == e.getParentId()){
                    m.getChildren().add(e);
                }
            }
            if(m.getParentId() == 0L){
                new_menu.add(m);
            }
        }
        return new_menu;
    }
}
