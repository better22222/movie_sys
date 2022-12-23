package com.system.utils;

import cn.hutool.crypto.digest.DigestUtil;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    //调用MD5加密
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    //定义加密用支付基数
    private static final String privateKey = "1a1aa";

    //密码加工
    public static String inputPassToNewPass(String pass){
        String  newPass =  privateKey.charAt(0)+privateKey.charAt(1)+pass +"";
        return newPass;
    }

    public static void main(String[] args){
        System.out.println(MD5Utils.md5(MD5Utils.inputPassToNewPass("123456")));
    }


}
