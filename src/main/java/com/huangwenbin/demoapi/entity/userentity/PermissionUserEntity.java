package com.huangwenbin.demoapi.entity.userentity;

import com.huangwenbin.demoapi.entity.generateentity.UserEntity;

import java.io.Serializable;
import java.util.List;

public class PermissionUserEntity extends UserEntity implements Serializable {
    private List<String> roleStrlist;

    private List<String> perminsStrlist;

    public List<String> getRoleStrlist() {
        return roleStrlist;
    }

    public void setRoleStrlist(List<String> roleStrlist) {
        this.roleStrlist = roleStrlist;
    }

    public List<String> getPerminsStrlist() {
        return perminsStrlist;
    }

    public void setPerminsStrlist(List<String> perminsStrlist) {
        this.perminsStrlist = perminsStrlist;
    }
}
