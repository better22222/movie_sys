package com.system.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Arrangement;
import com.system.entity.Film;
import com.system.mapper.FilmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-12
 */
@RestController
@RequestMapping("/system/arrangement")
public class ArrangementController extends BaseController {

    @Autowired
    FilmMapper filmMapper;
    //获得所有的用户分页数据
    @GetMapping("/list")
    public Result list(String name) {
        Page<Arrangement> arrangementPages = arrangementService.page(getPage(), new QueryWrapper<Arrangement>().like(StrUtil.isNotBlank(name), "name", name));
        arrangementPages.getRecords().forEach(arrangementPage -> {
            arrangementPage.setName(arrangementService.getById(arrangementPage.getId()).getName());
        });
        return Result.success(arrangementPages);
    }

    //获取电影名字
    @GetMapping("/name")

    public Result name(){
        List<Map> list=new ArrayList<>();
        filmService.list().forEach(film -> {
            Map<Object, Object> map = MapUtil.builder().put("value", film.getId())
                    .put("label", film.getName())
                    .map();
            list.add(map);
        });
        return Result.success(list);
    }

    //获取电影类型
//    @GetMapping("/type")
//    @PreAuthorize("hasAuthority('sys:categroy:list')")
//    public Result type(){
//        List<Map> list=new ArrayList<>();
//        categoryService.list().forEach(category -> {
//            Map<Object, Object> map = MapUtil.builder().put("value", category.getId())
//                    .put("label", category.getName())
//                    .map();
//            list.add(map);
//        });
//        return Result.success(list);
//    }


    //新建用户
    @PostMapping("/save")
    public Result save(@RequestBody Arrangement arrangement){
        //设置保存新用户的时间为当前时间
        arrangement.setCreated(LocalDateTime.now());
        arrangement.setStatu(Const.STATUS_ON);  //设置新建的状态
        Film film = filmMapper.selectOne(new QueryWrapper<Film>().eq("name", arrangement.getName()));
        arrangement.setFid(film.getId());
//        Category category = categoryMapper.selectOne(new QueryWrapper<Category>().eq("name",arrangement.getType()));
//        arrangement.setType(category.getName());
        arrangementService.save(arrangement);
        return Result.success(arrangement);
    }

    //编辑菜单-通过菜单编号id获得该菜单的详细数据
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id) {
        //调用MP生成的方法，根据id查询单个菜单对象信息
        return Result.success(arrangementService.getById(id));
    }

    //更新菜单
    @PostMapping("/update")
    public Result update(@RequestBody Arrangement arrangement){
        arrangement.setUpdated(LocalDateTime.now());
        arrangementService.updateById(arrangement);
        return Result.success(arrangement);
    }

    //删除用户
    @PostMapping("/delete")
    public Result del(@RequestBody Long[] ids){
        arrangementService.removeByIds(Arrays.asList(ids));
        return Result.success("");
    }
}
