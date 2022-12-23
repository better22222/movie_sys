package com.system.common;

import lombok.Data;

@Data
public class Result {
    private int code;
    private String message;
    private Object data;

    public static Result success(int code, String message, Object data){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static Result success(Object data){
        return success(200,"操作成功",data);
    }

    public static Result fail(int code, String message, Object data){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static Result fail(String message, Object data){
        return fail(400,message,data);
    }

    public static Result fail(String message){
        return fail(400,message,null);
    }
}
