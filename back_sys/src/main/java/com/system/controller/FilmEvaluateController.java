package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.entity.Film;
import com.system.entity.FilmEvaluate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
 * @since 2022-12-09
 */
@RestController
@Slf4j
@RequestMapping("/system/evaluate")
public class FilmEvaluateController extends BaseController {
    //获得所有评论的信息
    @GetMapping("/list")
    //@PreAuthorize("hasAuthority('sys:evaluate:list')")
    public Result list(String name){
        List<FilmEvaluate> filmes = new ArrayList<>();
        Page<FilmEvaluate> filmEvaluatePage = null;
        log.error(name);
        if(StringUtils.isNotBlank(name)){
            Film film = filmService.getOne(new QueryWrapper<Film>()
                    .eq("name", name)
            );

            filmEvaluatePage = filmEvaluateService.page(getPage(),new QueryWrapper<FilmEvaluate>()
                .eq("fid",film.getId())
            );
        }else {
            filmEvaluatePage = filmEvaluateService.page(getPage());
        }

        filmEvaluatePage.getRecords().forEach(filmEvaluate->{
            filmEvaluate.setFilmName(filmService.getById(filmEvaluate.getFid()).getName());
            filmEvaluate.setFansName(fansService.getById(filmEvaluate.getUid()).getUsername());
        });

        return Result.success(filmEvaluatePage);
    }


    //删除
    @PostMapping("/delete")
    public Result del(@RequestBody Long[] evaluateIds){
        filmEvaluateService.removeByIds(Arrays.asList(evaluateIds));
        return Result.success("");
    }

}
