package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.entity.Category;
import com.system.entity.Fans;
import com.system.entity.User;
import com.system.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@Slf4j
@RequestMapping("/system/fans")
public class FansController extends BaseController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //获得所有的粉丝信息
    @GetMapping("/list")
    public Result list(String name){ //name是搜索栏中内容，有name就进行模糊查询
        //查询分页数据
        Page page = getPage();
        Page<Fans> fansPage = fansService.page(page,new QueryWrapper<Fans>()
                .like(StrUtil.isNotBlank(name),"username",name)
        );
        return Result.success(fansPage);
    }

    //根据角色id获得粉丝信息
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id){
        Fans byId = fansService.getById(id);
        return Result.success(byId);
    }

    //编辑或添加粉丝
    @PostMapping("/update")
    public Result update(@RequestBody Fans fans){


        if(fansService.getById(fans.getId()) != null){
            //设置角色更新时间
            fans.setUpdated(LocalDateTime.now());
            fansService.updateById(fans);
            log.error("更新");
            return Result.success("更新信息成功");
        }else{
            //设置角色更新时间
            fans.setCreated(LocalDateTime.now());
            fansService.save(fans);
            log.error("添加");
            return Result.success("添加信息成功");
        }

    }


    //删除分类
    @PostMapping("/delete")
    @Transactional
    public Result delete(@RequestBody Long[] fansIds){
        fansService.removeByIds(Arrays.asList(fansIds));
        return Result.success("删除成功");
    }

    //重置密码
    @PostMapping("/repass/{id}")
    public Result repass(@PathVariable Long id){
        Fans byId = fansService.getById(id);
        byId.setPassword(MD5Utils.md5(MD5Utils.inputPassToNewPass(Const.DEFAULT_PASSWORD)));
        byId.setUpdated(LocalDateTime.now());
        fansService.updateById(byId);
        return Result.success("");
    }


}
