package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Arrangement;
import com.system.entity.Fans;
import com.system.entity.Film;
import com.system.mapper.FilmMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
import java.util.HashMap;
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
@Slf4j
@RequestMapping("/system/film")
public class FilmController extends BaseController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private FilmMapper filmMapper;

    //获得所有的电影信息
    @GetMapping("/list")
    public Result list(String name){ //name是搜索栏中内容，有name就进行模糊查询
        //查询分页数据
        log.error("name----"+name);
        Page page = getPage();
        Page<Film> filmPage =filmService.page(page,new QueryWrapper<Film>()
                .like(StrUtil.isNotBlank(name),"name",name)
        );
        return Result.success(filmPage);
    }

    //获得电影id信息
    @GetMapping("/info/{id}")
    public Result byID(@PathVariable Long id){
        Film byId = filmService.getById(id);
        return Result.success(byId);
    }

    //新建电影
    @PostMapping("/save")
    public Result save(@RequestBody Film film){
        //设置保存新用户的时间为当前时间
        film.setCreated(LocalDateTime.now());
        film.setStatu(Const.STATUS_ON);  //设置新建的状态
        filmService.save(film);
        return Result.success(film);
    }

    //新建电影
    @PostMapping("/update")
    public Result update(@RequestBody Film film){
        //设置保存新用户的时间为当前时间
        film.setUpdated(LocalDateTime.now());
        filmService.updateById(film);
        return Result.success(film);
    }


    @GetMapping("/getFilmByRegion")
    public Result gerFilmByRegion(){
        List<HashMap<String, Long>> filmByRegion = filmMapper.getFilmByRegion();
        System.out.println(filmByRegion);
        return Result.success(filmByRegion);
    }

}
