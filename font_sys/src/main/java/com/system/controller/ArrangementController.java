package com.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.system.common.R;
import com.system.common.ResultCode;
import com.system.entity.Arrangement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.system.common.BaseController;

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
@RequestMapping("/arrangement")
public class ArrangementController extends BaseController {
    //通过电影id获得所有的电影排片信息
    @GetMapping("/list/{fid}")
    public R list(@PathVariable Long fid){
        List<Arrangement> arrangements = arrangementService.list(
                new QueryWrapper<Arrangement>()
                .eq("fid",fid)
        );
        if (arrangements == null) {
            return R.error(ResultCode.DATA_NOT_FOUND,"未找到排片信息");
        }else{
            return R.ok().data("arrangements",arrangements);
        }

    }
}
