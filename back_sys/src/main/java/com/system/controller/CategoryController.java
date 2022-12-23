package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Category;
import com.system.entity.Role;
import com.system.entity.RoleMenu;
import com.system.entity.UserRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-07
 */
@RestController
@RequestMapping("/system/category")
public class CategoryController extends BaseController {

    //获得所有的类别信息
    @GetMapping("/list")
    public Result list(String name){ //name是搜索栏中内容，有name就进行模糊查询
        //查询分页数据
        Page page = getPage();
        Page<Category> categoryPage = categoryService.page(page,new QueryWrapper<Category>()
                .like(StrUtil.isNotBlank(name),"name",name)
        );
        return Result.success(categoryPage);
    }

    //根据角色id获得分类信息
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id){
        Category byId = categoryService.getById(id);
        return Result.success(byId);
    }

    //编辑分类
    @PostMapping("/update")
    public Result update(@RequestBody Category category){
        //设置角色更新时间
        category.setUpdated(LocalDateTime.now());
        categoryService.updateById(category);
        return Result.success(category);
    }

    //新建分类
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        //设置角色更新时间
        category.setCreated(LocalDateTime.now());
        categoryService.save(category);
        return Result.success(category);
    }

    //删除分类
    @PostMapping("/delete")
    @Transactional
    public Result delete(@RequestBody Long[] categoryIds){

        categoryService.removeByIds(Arrays.asList(categoryIds));
        return Result.success("删除成功");
    }

}
