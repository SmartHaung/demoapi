package com.huangwenbin.demoapi.enumeration;

public enum RequestFailCodeEnum {
    PERMISSION_FIAL_NO_LOGIN(1, "您未登录"),
    PERMISSION_FIAL_PASSWORD_ERROR(2, "密码错误"),
    PERMISSION_FIAL_NO_SUCH_ACCOUNT(3, "未找到用户"),
    PERMISSION_FIAL_ACCOUNT_LOCKED(4, "账号锁定"),
    PERMISSION_FIAL_UNKNOWM_ERROR(5, "未知错误"),
    LOGIN_FIAL_ACCOUNT_CAN_NOT_BE_EMPTY(6, "账号不能为空"),
    LOGIN_FIAL_PASSWORD_CAN_NOT_BE_EMPTY(7, "密码不能为空"),
    PERMISSION_FIAL_NO_PERMISSION(8, "没有相关权限"),
    QUERY_ROLE_FIAL_PARAMETER_ERROR(9, "参数错误"),;
    private int code = 0;
    private String msg = "";

    RequestFailCodeEnum(int code, String msg) {
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
