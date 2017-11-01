package com.huangwenbin.demoapi.service;

import com.huangwenbin.demoapi.entity.generateentity.RoleEntity;
import com.huangwenbin.demoapi.entity.generateentity.RolePermissionEntity;
import com.huangwenbin.demoapi.entity.generateentity.UserRoleEntity;
import com.huangwenbin.demoapi.entity.userentity.RoleInfoEntity;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<RoleEntity> queryRoleByUid(Long userRoleUid);

    List<RoleEntity> queryAllRole();

    List<RoleInfoEntity> queryRole(Map<String, Long> searchMap);

    int countRole(Map<String, Long> searchMap);

    int removeRole(Map<String, Long> conditionMap);

    int addRole(RoleEntity roleEntity);

    int editRole(RoleEntity roleEntity);

    boolean editRolePermission(Long roleId, List<RolePermissionEntity> rolePermissionEntityList);

    int countUserInRole(Map<String, Long> searchMap);

    int addUserIntoRole(UserRoleEntity userRoleEntity);

    int removeUserFromRole(UserRoleEntity userRoleEntity);
}
