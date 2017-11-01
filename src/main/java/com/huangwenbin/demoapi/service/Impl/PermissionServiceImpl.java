package com.huangwenbin.demoapi.service.Impl;

import com.huangwenbin.demoapi.entity.generateentity.PermissionEntity;
import com.huangwenbin.demoapi.enumeration.PublicStatusEnum;
import com.huangwenbin.demoapi.mapper.PermissionMapper;
import com.huangwenbin.demoapi.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class description
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/27
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    /**
     * Field description
     */
    @Autowired
    private PermissionMapper permissionEntityMapper;

    @Override
    public int addPermission(PermissionEntity permissionEntity) {
        if (permissionEntity != null) {
            permissionEntity.setPermissionStatus((byte) PublicStatusEnum.PERMISSION_IN_USE.getCode());
            return permissionEntityMapper.insertSelective(permissionEntity);
        }
        return 0;
    }

    @Override
    public int editPermission(PermissionEntity permissionEntity) {
        if ((permissionEntity != null)
                && (permissionEntity.getPermissionId() != null)
                && (permissionEntity.getPermissionId() != 0)) {
            return permissionEntityMapper.updateByPrimaryKeySelective(permissionEntity);
        }
        return 0;
    }

    @Override
    public List<PermissionEntity> queryAllPermission() {
        return permissionEntityMapper.queryAllPermission();
    }

    @Override
    public List<PermissionEntity> queryPermissionByRoleId(Long roleId) {
        return permissionEntityMapper.queryPermissionByRoleId(roleId);
    }

    @Override
    public List<PermissionEntity> queryPermissionByUid(Long userid) {
        return permissionEntityMapper.queryPermissionByUid(userid);
    }

    @Override
    public int removePermission(PermissionEntity permissionEntity) {
        if ((permissionEntity != null)
                && (permissionEntity.getPermissionId() != null)
                && (permissionEntity.getPermissionId() != 0)) {
            permissionEntity.setPermissionStatus((byte) PublicStatusEnum.PERMISSION_NOT_IN_USE.getCode());
            return permissionEntityMapper.updateByPrimaryKeySelective(permissionEntity);
        }
        return 0;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
