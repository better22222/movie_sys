package com.system.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.system.common.BaseController;

import com.system.common.R;
import com.system.common.ResultCode;
import com.system.entity.Arrangement;
import com.system.entity.Fans;
import com.system.entity.Film;
import com.system.entity.Orders;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    @PostMapping("/save")
    public R save(@RequestBody Orders orders){

        int id = (int)(Math.random()*100) * (int)(Math.random()*100) +123;
        System.out.println(id);
        orders.setId((new Long(id)));
        orders.setPayAt(LocalDateTime.now());
        log.error("data___"+orders);
        ordersService.save(orders);
        return R.ok().data("orders",orders);
    }

    @GetMapping("/info/{aid}")
    public R info(@PathVariable Long aid){
        List<Orders> orderList = ordersService.list(new QueryWrapper<Orders>()
                .eq("aid", aid)
        );
        if(orderList !=null){
            return R.ok().data("orderList",orderList);
        }else {
            return R.error(ResultCode.DATA_NOT_FOUND,"场次购买信息查询失败");
        }
    }

    @GetMapping("/list/{id}")
    public R list(@PathVariable Long id){
        //查询对应用户的所有订单
        List<Orders> orderByFid = ordersService.list(new QueryWrapper<Orders>()
                .eq("uid", id)
                .orderByDesc("pay_at")
        );

        orderByFid.forEach(order->{

            order.setUserName(fansService.getOne(new QueryWrapper<Fans>().eq("id",order.getUid())).getUsername());
            order.setFilmName(arrangementService.getOne(new QueryWrapper<Arrangement>().eq("id",order.getAid())).getName());

            Arrangement a =  arrangementService.getOne(new QueryWrapper<Arrangement>()
                    .eq("id",order.getAid())
            );
            Film f = filmService.getById(a.getFid());
            String cover = f.getCover();
            order.setFilmCover(cover);

        });

        return R.ok().data("orderList",orderByFid);
    }


}
