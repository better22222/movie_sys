package com.system.common;

public class ResultCode {
    public static final Integer SUCCESS = 1;

    //参数错误
    public static final Integer USER_NOT_EXIST = 20001; //用户不存在
    public static final Integer LOGIN_NOT_EXIST = 20002;//用户未登录
    public static final Integer USER_ACCOUNT_ERROR = 20003; //  密码错误
    public static final Integer USER_ACCOUNT_FORBIDDEN = 20004; //用户账户被禁用
    public static final Integer USER_HAS_EXIST = 20005;  //用户已存在
    public static final Integer USER_HAS_EXPIRED = 20006; //用户过期 token过期
    public static final Integer DATA_NOT_FOUND = 20007; //未找到数据

    //token验证
    public static final Integer TOKEN_NOT_EXIST = 30001;
    public static final Integer TOKEN_ERROR = 30002;

    public static final Integer TO_COMMENT_ERROR =  325800;
}
