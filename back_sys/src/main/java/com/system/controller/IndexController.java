package com.system.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.system.common.BaseController;
import com.system.common.Result;
import com.system.common.lang.Const;
import com.system.config.KaptchaConfig;
import com.system.entity.User;

import com.system.entity.dto.MenuDto;
import com.system.util.OSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@RestController
public class IndexController extends BaseController {
    @Autowired
    private KaptchaConfig kaptchaConfig;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        DefaultKaptcha producer = kaptchaConfig.createCaptcha();
        String code = producer.createText();//生成验证码字符
        String key = UUID.randomUUID().toString(); //生成uuid作为存储到redis的key
        BufferedImage image = producer.createImage(code);   //将验证码字符存储至内存图片中
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        //base64转码对象
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String imgEncode = encoder.encode(outputStream.toByteArray());

        //存储redis中，判断登录输入的验证码是否正确
        redisUtil.hset(Const.CAPTCHA_KEY,key,code);

        log.info("验证码--{} --{}",key,code);

        //返回一个map集合，存储多个数据
        return Result.success(
                MapUtil.builder()
                .put("key",key)
                .put("captchaImg",str+imgEncode)
                .build()
        );
    }

    //获得所有的用户信息
    @GetMapping("/user/list")
    @PreAuthorize("hasAnyAuthority('sys:user:list')")
    public Result list(){
        List<User> users = userService.list();
        Result result = new Result().success(users);
        return result;
    }

    //登录成功后获得用户的详细信息
    @GetMapping("/user/userInfo")
    public Result userinfo(Principal principal){
        String name = principal.getName();
        User userByUserName = userService.getUserByUserName(name);
        return Result.success(
                MapUtil.builder()
                .put("id",userByUserName.getId())
                .put("username",userByUserName.getUsername())
                .put("avatar",userByUserName.getAvatar())
                .build()
        );
    }

    //请求获得菜单数据的接口, 参数：principal是 sc登录后封装的Authentication对象中的用户身份主体对象
    @GetMapping("/menu/nav")
    public Result nav(Principal principal){
        //获得登录的用户名
        String username = principal.getName();
        User user = userService.getUserByUserName(username);

        //获取用户权限
        String userAuthorityInfo = userService.getUserAuthorityInfo(user.getId());
        //将字符串转换为数组
        String[] authorityInfo = StringUtils.tokenizeToStringArray(userAuthorityInfo, ",");
        //获得菜单导航栏信息
        List<MenuDto> currentUserNav = menuService.getCurrentUserNav(username);

        return Result.success(
                MapUtil.builder()
                .put("authoritys",authorityInfo)
                .put("nav",currentUserNav)
                .build()
        );
    }

    //上传图片的方法
    @PostMapping("/upload")
    public Result uploadFile(@RequestBody MultipartFile file) throws Exception{
        try {
            //得到文件的名字x
            String originalFilename = file.getOriginalFilename();
            String fileUrl = OSSUtil.uploadFile(file.getInputStream(), originalFilename);
            // 上传成功之后的文件地址 写给前端
            return Result.success(fileUrl);
        }catch(IOException e){
            e.printStackTrace();
            return Result.fail("图片上传失败");
            //1231231
        }
    }
    /**
     * 文件复制
     * @param src
     * @param destDir
     * @param fileName
     * @throws IOException
     */
    public void copyFile(String src,String destDir,String fileName) throws IOException{
        FileInputStream in=new FileInputStream(src);
        File fileDir = new File(destDir);
        if(!fileDir.isDirectory()){
            fileDir.mkdirs();
        }
        File file = new File(fileDir,fileName);

        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream out=new FileOutputStream(file);
        int c;
        byte buffer[]=new byte[1024];
        while((c=in.read(buffer))!=-1){
            for(int i=0;i<c;i++){
                out.write(buffer[i]);
            }

        }
        in.close();
        out.close();
    }

}
