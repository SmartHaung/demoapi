package com.huangwenbin.demoapi.entity.userentity;

import com.huangwenbin.demoapi.entity.generateentity.RoleEntity;

public class RoleInfoEntity extends RoleEntity {
    private String userTruename;

    public String getUserTruename() {
        return userTruename;
    }

    public void setUserTruename(String userTruename) {
        this.userTruename = userTruename;
    }
}
