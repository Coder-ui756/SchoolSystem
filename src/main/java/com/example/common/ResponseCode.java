package com.example.common;

public enum ResponseCode {

    USERNAME_NOT_EMPTY(1, "用户名不能为空"),
    PASSWORD_NOT_EMPTY(2, "密码不能为空"),
    USERNAME_NOT_EXISTS(3,"用户名不存在"),
    PASSWORD_ERROR(4, "密码错误"),
    PARAMTER_NOT_EMPTY(5, "参数不能为空"),
    EMAIL_NOT_EMPTY(6, "邮箱不能为空"),
    PHONE_NOT_EMPTY(7, "手机号不能为空"),
    ROLE_NOT_EMPTY(8, "请选择登录身份"),
    USERNAME_EXISTS(9, "用户名已经存在"),
    EMAIL_EXISTS(10, "邮箱已经存在"),
    REGISTER_FAIL(11, "注册失败")

    ;

    private int code;
    private String msg;

    ResponseCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
