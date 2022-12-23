package com.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.R;
import com.system.entity.Category;
import com.system.entity.Poster;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
    @GetMapping("/list")
    public R list(){
        List<Category> list = categoryService.list(new QueryWrapper<Category>()
            .eq("statu",1)
        );
        return R.ok().data("category",list);
    }

    //根据标签id组获得对应的标签名
    @GetMapping("/byId/{ids}")
    public R byId(@PathVariable String ids){
        List<String> cNames = categoryService.getCName(ids);
        return R.ok().data("categoryNames",cNames);
    }

}
