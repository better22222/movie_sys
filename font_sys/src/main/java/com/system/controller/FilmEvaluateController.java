package com.system.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.R;
import com.system.common.ResultCode;
import com.system.entity.Film;
import com.system.entity.FilmEvaluate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;
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
@RequestMapping("/evaluate")
public class FilmEvaluateController extends BaseController {

    @GetMapping("/info/{fid}")
    public R info(@PathVariable Long fid){
        Page<FilmEvaluate> filmEvaluatePage = filmEvaluateService.page(getPage(),
                new QueryWrapper<FilmEvaluate>()
                .eq("fid", fid)
                .orderByDesc("created")
        );

        filmEvaluatePage.getRecords().forEach(filmEvaluate->{
            filmEvaluate.setFilmName(filmService.getById(filmEvaluate.getFid()).getName());
            filmEvaluate.setFansName(fansService.getById(filmEvaluate.getUid()).getUsername());
            filmEvaluate.setFansUrl(fansService.getById(filmEvaluate.getUid()).getAvatar());
        });

        return R.ok().data("filmEvaluate",filmEvaluatePage);
    }

    @PostMapping("/putComment")
    public R putComment(Long fid,Long uid, Integer star, String comment ){
        log.error("fid-----"+fid);
        log.error("uid-----"+uid);
        log.error("fid-----"+star);
        log.error("fid-----"+comment);
        FilmEvaluate filmEvaluate = new FilmEvaluate();
        filmEvaluate.setFid(fid);
        filmEvaluate.setUid(uid);
        filmEvaluate.setStar(star);
        filmEvaluate.setComment(comment);
        filmEvaluate.setCreated(LocalDateTime.now());
        filmEvaluate.setStatu(1);
        boolean save = filmEvaluateService.save(filmEvaluate);
        if(save )
        {
            return R.ok().data("filmEvaluate",filmEvaluate);
        }else{
            return R.error(ResultCode.TO_COMMENT_ERROR,"添加评论错误");
        }

    }


}
