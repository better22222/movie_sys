package com.system.controller;



import cn.hutool.core.util.StrUtil;
import com.system.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;

import com.system.mapper.OrdersMapper;
import com.system.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-12
 */
@RestController
@Slf4j
@RequestMapping("/system/orders")
public class OrdersController extends BaseController {

    @Autowired
    private OrdersMapper ordersMapper;

    @GetMapping("/list")
    public Result list(String name) { //name就是搜索栏中的内容，有name就是模糊查询，没有就全部查出来

        List<Long> longs = new ArrayList<>();
        // 根据用户名从fans表中查询出id，放进列表里
        fansService.list(new QueryWrapper<Fans>().like("username", name)).forEach(item -> {
            longs.add(item.getId());
        });

        //
        Page<Orders> orderPage = ordersService.page(getPage(), new QueryWrapper<Orders>()
                .in(StrUtil.isNotBlank(name), "uid", longs)
        );

        orderPage.getRecords().forEach(order -> {
            order.setUserName(fansService.getOne(new QueryWrapper<Fans>().eq("id", order.getUid())).getUsername());
            order.setFilmName(arrangementService.getOne(new QueryWrapper<Arrangement>().eq("id", order.getAid())).getName());
        });
        return Result.success(orderPage);
    }


    //删除粉丝信息
    @PostMapping("/delete")
    @Transactional
    public Result del(@RequestBody Long[] orderIds){
        ordersService.removeByIds(Arrays.asList(orderIds));
        return Result.success("");
    }


    @GetMapping("/getTime")
    public Result GetTime(){
        List<HashMap<String, Long>> orderByTime = ordersMapper.getOrderByTime();
        return Result.success(orderByTime);
    }
}
