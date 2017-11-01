package com.huangwenbin.demoapi.enumeration;

public enum PublicStatusEnum {
    PERMISSION_IN_USE(1, "权限有效"),
    PERMISSION_NOT_IN_USE(2, "权限无效"),
    ROLE_IN_USE(1, "角色有效"),
    ROLE_NOT_IN_USE(2, "角色无效"),
    ROLE_PERMISSION_IN_USE(1, "角色权限对应关系有效"),
    ROLE_PERMISSION_NOT_IN_USE(2, "角色权限对应关系无效"),
    USER_ROLE_IN_USE(1, "角色成员对应关系有效"),
    USER_ROLE_NOT_IN_USE(2, "角色成员对应关系无效");

    private int code = 0;
    private String msg = "";

    PublicStatusEnum(int code, String msg) {
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
