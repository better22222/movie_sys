package com.system.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.service.*;
import com.system.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    //每个控制器用到的对象

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public RedisUtil redisUtil;

    @Autowired
    public FilmService filmService;

    @Autowired
    public UserService userService;

    @Autowired
    public MenuService menuService;

    @Autowired
    public OrdersService ordersService;

    @Autowired
    public FilmEvaluateService filmEvaluateService;

    @Autowired
    public ArrangementService arrangementService;

    @Autowired
    public RoleService roleService;

    @Autowired
    public PosterService posterService;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public FansService fansService;



    @Autowired
    public RoleMenuService roleMenuService;

    @Autowired
    public UserRoleService userRoleService;

    //获取分页方法 --前端传递参数：current，size，得到参数 封装为Page对象
    public Page getPage(){
        //得到分页数据的当前页码
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        //得到一页显示的页数
        int size = ServletRequestUtils.getIntParameter(request, "page", 5);
        return new Page(current,size);
    }

}