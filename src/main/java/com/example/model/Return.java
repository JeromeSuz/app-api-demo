package com.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 请求响应实体
 *
 * @author jerome
 * @date 2017/3/22 14:46
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // null不序列化
public class Return {

    // 返回列表
    public static final Return IS_SUCCESS = new Return(10000, "响应成功");
    public static final Return IS_ERROR = new Return(-10000, "连接错误");
    public static final Return PARAM_IS_ERROR = new Return(-10000, "参数有误");

    // 登陆登出相关
    public static final Return ACCOUNT_LOCK = new Return(-20001, "密码输入错误达到上限，帐号锁定");
    public static final Return LOGIN_SUCCESS = new Return(20002, "登陆成功");
    public static final Return USER_ERROR = new Return(-20003, "用户不存在");
    public static final Return PWD_ERROR = new Return(-20004, "密码错误");
    public static final Return LOGOUT_SUCCESS = new Return(20005, "登出成功");
    public static final Return IS_NOT_LOGIN = new Return(-20006, "未登录");
    public static final Return LOGIN_TIME_OUT = new Return(-20007, "登陆超时，请重新登录");


    // 状态码
    private Integer code;
    // 返回信息
    private String msg;
    // 返回对象
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Return(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Return(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
