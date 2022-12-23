package com.system.common;

import lombok.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class R {
    private Integer code; //状态码
    private boolean success;//请求是否成功
    private String message;

    //data存储返回数据：Map名-值
    private Map<String,Object> data =new HashMap<>();

    //请求成功的操作
    public static R ok(){
        R r =new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("请求成功");
        return r;
    }
    public static R error(Integer code,String message){
        R r =new R();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    //赋值Data
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
}
