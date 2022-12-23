package com.system.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.R;
import com.system.entity.Poster;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

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
@RequestMapping("/poster")
public class PosterController extends BaseController {

    @GetMapping("/list")
    public R list(){
        List<Poster> list = posterService.list(new QueryWrapper<Poster>()
            .eq("statu",1)
        );
        return R.ok().data("poster",list);
    }


}