package com.system.common;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.system.entity.Category;
import com.system.entity.Fans;
import com.system.service.*;
import com.system.service.impl.QRCodeService;
import com.system.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;


public class BaseController {

    @Autowired
    public FansService fansService;

    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public FilmService filmService;

    @Autowired
    public PosterService   posterService;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public FilmEvaluateService filmEvaluateService;

    @Autowired
    public ArrangementService arrangementService;

    @Autowired
    public OrdersService ordersService;

    @Autowired
    public HttpServletRequest request;
    //获得分页数据 前端请求传递参数：current,size 得到参数封装为Page对象（MP分页使用
    public Page getPage(){
        //得到分页数据的当前页码
        int current = ServletRequestUtils.getIntParameter(request,"current",1);
        //得到一页显示的记录数
        int size = ServletRequestUtils.getIntParameter(request,"size",5);

        int pages = ServletRequestUtils.getIntParameter(request,"pages",10);
        return new Page(current,size);
    }

}
