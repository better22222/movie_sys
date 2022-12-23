package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Poster;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/system/poster")
public class PosterController extends BaseController {

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:poster:list')")
    public Result list(String title){
        Page<Poster> posters=posterService.page(getPage(),new QueryWrapper<Poster>().like(StrUtil.isNotBlank(title),"title",title));
        return Result.success(posters);
    }

    //编辑轮播图信息
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id){
        return Result.success(posterService.getById(id));
    }

    //保存新菜单
    @PostMapping("/save")
    public Result save(@RequestBody Poster poster){
        poster.setCreated(LocalDateTime.now());
        poster.setStatu(Const.STATE_ON);
        posterService.save(poster);
        return Result.success(poster);
    }

    //更新菜单
    @PostMapping("/update")
    public Result update(@RequestBody Poster poster){
        poster.setUpdated(LocalDateTime.now());
        posterService.updateById(poster);
        return Result.success(poster);
    }

    @PostMapping("/delete")
    @Transactional
    public Result del(@RequestBody Long[] posterIds){
        posterService.removeByIds(Arrays.asList(posterIds));
        return Result.success("");
    }

}
