package com.huangwenbin.demoapi.service;

import java.util.List;

import com.huangwenbin.demoapi.entity.generateentity.PermissionEntity;

/**
 * Interface description 权限控制
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/27
 */
public interface PermissionService {

    /**
     * Method description 新增权限
     *
     * @param permissionEntity
     * @return
     */
    int addPermission(PermissionEntity permissionEntity);

    /**
     * Method description 修改权限
     *
     * @param permissionEntity
     * @return
     */
    int editPermission(PermissionEntity permissionEntity);

    /**
     * Method description 获取全部正在使用的权限
     *
     * @return 全部正在使用的权限列表
     */
    List<PermissionEntity> queryAllPermission();

    /**
     * Method description 查看角色拥有的权限
     *
     *
     * @param roleId
     *
     * @return
     */
    List<PermissionEntity> queryPermissionByRoleId(Long roleId);

    /**
     * Method description 获取当前用户的权限
     *
     * @param userid 用户ID
     * @return 用户拥有的权限列表
     */
    List<PermissionEntity> queryPermissionByUid(Long userid);

    /**
     * Method description 删除权限
     *
     * @param permissionEntity
     * @return
     */
    int removePermission(PermissionEntity permissionEntity);
}


//~ Formatted by Jindent --- http://www.jindent.com
