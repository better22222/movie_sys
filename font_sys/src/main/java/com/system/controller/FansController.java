package com.system.controller;
import com.system.common.R;
import com.system.common.ResultCode;
import com.system.entity.Fans;
import com.system.utils.MD5Utils;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */

@CrossOrigin
@RestController
@RequestMapping("/fans")
public class FansController extends BaseController {
    @GetMapping("/test")
    public R test(){
        String name = "stevejobs";//数据
        return R.ok().data("name",name);
    }

    @PostMapping("/chang")
    public R change(Fans fans){
        fans.setUpdated(LocalDateTime.now());
        fansService.updateById(fans);
        return R.ok().data("fans",fans);
    }


    @GetMapping("/check/{id}")
    public R check(@PathVariable Long id){
        Fans byId = fansService.getById(id);
        if(byId !=null){
            return R.error(ResultCode.USER_HAS_EXIST,"改手机号已存在");
        }else{
            return R.ok();
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody Fans fans){
        fans.setCreated(LocalDateTime.now());
        fans.setPassword(MD5Utils.md5(MD5Utils.inputPassToNewPass(fans.getPassword())));
        fansService.save(fans);
        return  R.ok().data("fans",fans);
    }


    @PostMapping("/login")
    public R login(@RequestBody Fans fans){
        Fans fansObj = fansService.getById(fans.getId());
        if(fansObj != null){
            if (fansObj.getStatu() == 0) {
                return R.error(ResultCode.USER_ACCOUNT_FORBIDDEN,"账户被禁用");
            }
            System.out.println("密码"+MD5Utils.md5(MD5Utils.inputPassToNewPass(fans.getPassword())));
            if(fansObj.getPassword().equals(MD5Utils.md5(MD5Utils.inputPassToNewPass(fans.getPassword())))){
                fansObj.setToken(jwtUtils.generateToken(fansObj.getId()+""));
                return R.ok().data("fans",fansObj);
            }
            return R.error(ResultCode.USER_ACCOUNT_ERROR,"密码错误");
        }
        return R.error(ResultCode.USER_NOT_EXIST,"改用户不存在");
    }


}
