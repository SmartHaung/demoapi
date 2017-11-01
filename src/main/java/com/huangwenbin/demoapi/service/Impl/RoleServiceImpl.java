package com.huangwenbin.demoapi.service.Impl;

import com.huangwenbin.demoapi.entity.generateentity.RoleEntity;
import com.huangwenbin.demoapi.entity.generateentity.RolePermissionEntity;
import com.huangwenbin.demoapi.entity.generateentity.UserEntity;
import com.huangwenbin.demoapi.entity.generateentity.UserRoleEntity;
import com.huangwenbin.demoapi.entity.userentity.RoleInfoEntity;
import com.huangwenbin.demoapi.enumeration.PublicStatusEnum;
import com.huangwenbin.demoapi.mapper.RoleMapper;
import com.huangwenbin.demoapi.mapper.RolePermissionMapper;
import com.huangwenbin.demoapi.mapper.UserMapper;
import com.huangwenbin.demoapi.mapper.UserRoleMapper;
import com.huangwenbin.demoapi.service.RoleService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleEntityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    private static SqlSessionFactory sqlSessionFactory;

    @Override
    public List<RoleEntity> queryRoleByUid(Long userRoleUid) {
        return roleEntityMapper.queryRoleByUid(userRoleUid);
    }

    @Override
    public List<RoleEntity> queryAllRole() {
        return roleEntityMapper.queryAllRole();
    }

    @Override
    public List<RoleInfoEntity> queryRole(Map<String, Long> searchMap) {
        List<RoleEntity> roleEntityList = roleEntityMapper.queryRole(searchMap);
        List<RoleInfoEntity> roleInfoEntityList = new ArrayList<>();
        if (roleEntityList != null && roleEntityList.size() > 0) {
            Map<Long, String> user = new HashMap<>();
            for (RoleEntity roleEntity : roleEntityList) {
                RoleInfoEntity roleInfoEntity = new RoleInfoEntity();
                roleInfoEntity.setRoleName(roleEntity.getRoleName());
                roleInfoEntity.setRoleId(roleEntity.getRoleId());
                roleInfoEntity.setRoleCreatedate(roleEntity.getRoleCreatedate());
                roleInfoEntity.setRoleCreateuser(roleEntity.getRoleCreateuser());
                if (user.containsKey(roleEntity.getRoleCreateuser())) {
                    roleInfoEntity.setUserTruename(user.get(roleEntity.getRoleCreateuser()));
                } else {
                    UserEntity userEntity = userMapper.selectByPrimaryKey(roleEntity.getRoleCreateuser());
                    if (userEntity != null && userEntity.getUserTruename() != null) {
                        roleInfoEntity.setUserTruename(userEntity.getUserTruename());
                        user.put(roleInfoEntity.getRoleCreateuser(), userEntity.getUserTruename());
                    }
                }
                roleInfoEntityList.add(roleInfoEntity);
            }
        }
        return roleInfoEntityList;
    }

    @Override
    public int countRole(Map<String, Long> searchMap) {
        return roleEntityMapper.countRole(searchMap);
    }

    @Override
    public int removeRole(Map<String, Long> conditionMap) {
        if (conditionMap.containsKey("roleId") && conditionMap.get("roleId") != 0) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRoleId(conditionMap.get("roleId"));
            if (conditionMap.containsKey("modifyUser")) {
                roleEntity.setRoleModifyuser(conditionMap.get("modifyUser"));
            }
            roleEntity.setRoleModifydate(new Date());
            roleEntity.setRoleStatus((byte) PublicStatusEnum.ROLE_NOT_IN_USE.getCode());
            return roleEntityMapper.updateByPrimaryKeySelective(roleEntity);
        }
        return 0;
    }

    @Override
    public int addRole(RoleEntity roleEntity) {
        if (roleEntity != null) {
            return roleEntityMapper.insertSelective(roleEntity);
        }
        return 0;
    }

    @Override
    public int editRole(RoleEntity roleEntity) {
        if (roleEntity != null && roleEntity.getRoleId() != null && roleEntity.getRoleId() != 0) {
            return roleEntityMapper.updateByPrimaryKeySelective(roleEntity);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editRolePermission(Long roleId, List<RolePermissionEntity> rolePermissionEntityList) {
        if (roleId != null && roleId != 0) {
            rolePermissionMapper.updatePermissionToNoUseByRoleId(roleId);
            if (rolePermissionEntityList != null && rolePermissionEntityList.size() > 0) {
                rolePermissionMapper.BatchInsert(rolePermissionEntityList);
            }
        }
        return false;
    }

    @Override
    public int countUserInRole(Map<String, Long> searchMap) {
        if (searchMap.containsKey("roleId") && searchMap.get("roleId") != null && searchMap.get("roleId") != 0 && searchMap.containsKey("userId") && searchMap.get("userId") != null && searchMap.get("userId") != 0) {
            return userRoleMapper.countUserInRole(searchMap);
        }
        return 0;
    }

    @Override
    public int addUserIntoRole(UserRoleEntity userRoleEntity) {
        if (userRoleEntity != null) {
            return userRoleMapper.insertSelective(userRoleEntity);
        }
        return 0;
    }

    @Override
    public int removeUserFromRole(UserRoleEntity userRoleEntity) {
        if (userRoleEntity != null) {
            return userRoleMapper.updateByUserIdAndRoleId(userRoleEntity);
        }
        return 0;
    }
}
