package com.huangwenbin.demoapi.controller;

import com.huangwenbin.demoapi.common.Response;
import com.huangwenbin.demoapi.common.StringUtils;
import com.huangwenbin.demoapi.common.UserException.PermissionException;
import com.huangwenbin.demoapi.entity.generateentity.*;
import com.huangwenbin.demoapi.entity.userentity.RoleInfoEntity;
import com.huangwenbin.demoapi.enumeration.PublicStatusEnum;
import com.huangwenbin.demoapi.enumeration.RequestFailCodeEnum;
import com.huangwenbin.demoapi.service.PermissionService;
import com.huangwenbin.demoapi.service.RoleService;
import com.huangwenbin.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Class description 权限相关操作
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/27
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * Field description
     */
    @Autowired
    private UserService userService;

    /**
     * Field description
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * Field description
     */
    @Autowired
    private RoleService roleService;

    /**
     * Method description
     *
     * @param permissionEntity
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/addPermission")
    public void addPermission(PermissionEntity permissionEntity) throws IOException, PermissionException {
        userPermissionCheck("permission:add");
        if (permissionEntity != null) {
            permissionEntity.setPermissionCreateuser(getUser().getUserId());
            permissionEntity.setPermissionModifyuser(getUser().getUserId());
            permissionService.addPermission(permissionEntity);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("permissionId", permissionEntity.getPermissionId());
        resultMap.put("msg", "add permission success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleEntity
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/addRole")
    public void addRole(RoleEntity roleEntity) throws IOException, PermissionException {
        userPermissionCheck("role:add");
        if ((roleEntity != null) && !StringUtils.nullOrEmpty(roleEntity.getRoleName())) {
            roleEntity.setRoleCreateuser(getUser().getUserId());
            roleEntity.setRoleModifyuser(getUser().getUserId());
            roleEntity.setRoleCreatedate(new Date());
            roleEntity.setRoleModifydate(new Date());
            roleEntity.setRoleStatus((byte) PublicStatusEnum.ROLE_IN_USE.getCode());
            roleService.addRole(roleEntity);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "add role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleId
     * @param userId
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/addUserIntoRole")
    public void addUserIntoRole(Long roleId, Long userId) throws IOException, PermissionException {
        userPermissionCheck("role:addUserIntoRole");
        if ((roleId != null) && (roleId != 0) && (userId != null) && (userId != 0)) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();

            userRoleEntity.setUserRoleUid(userId);
            userRoleEntity.setUserRoleRid(roleId);
            userRoleEntity.setUserRoleStatus((byte) PublicStatusEnum.USER_ROLE_IN_USE.getCode());
            userRoleEntity.setUserRoleCreateuser(getUser().getUserId());
            userRoleEntity.setUserRoleCreatedate(new Date());
            userRoleEntity.setUserRoleModifyuser(getUser().getUserId());
            userRoleEntity.setUserRoleModifydate(new Date());
            roleService.addUserIntoRole(userRoleEntity);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "remove role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleId
     * @param userId
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/countUserInRole")
    public void countUserInRole(Long roleId, Long userId) throws IOException, PermissionException {
        userPermissionCheck("role:countUserInRole");

        int count = 0;

        if ((roleId != null) && (roleId != 0) && (userId != null) && (userId != 0)) {
            Map<String, Long> searchMap = new HashMap<>();

            searchMap.put("roleId", roleId);
            searchMap.put("userId", userId);
            count = roleService.countUserInRole(searchMap);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("count", count);
        resultMap.put("msg", "remove role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description 编辑权限
     *
     * @param permissionEntity
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/editPermission")
    public void editPermission(PermissionEntity permissionEntity) throws IOException, PermissionException {
        userPermissionCheck("permission:edit");

        Map<String, Object> resultMap = new HashMap<>();

        if (permissionEntity != null) {
            permissionEntity.setPermissionModifyuser(getUser().getUserId());
            permissionEntity.setPermissionModifydate(new Date());
            permissionService.editPermission(permissionEntity);
        }
        resultMap.put("msg", "edit permission success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleEntity
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/editRole")
    public void editRole(RoleEntity roleEntity) throws IOException, PermissionException {
        userPermissionCheck("role:add");
        if ((roleEntity != null) && !StringUtils.nullOrEmpty(roleEntity.getRoleName())) {
            roleEntity.setRoleModifyuser(getUser().getUserId());
            roleEntity.setRoleModifydate(new Date());
            roleService.editRole(roleEntity);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "edit role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleId
     * @param selectPermissions
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/editRolePermission")
    public void editRolePermission(Long roleId, String selectPermissions) throws IOException, PermissionException {
        userPermissionCheck("role:editPermission");
        if ((roleId != null) && (roleId != 0)) {
            List<RolePermissionEntity> rolePermissionEntityList = new ArrayList<>();

            if (!StringUtils.nullOrEmpty(selectPermissions)) {
                String[] permissionList = selectPermissions.replaceAll("\\[|\\]", "").split(",");

                if ((permissionList != null) && (permissionList.length > 0)) {
                    for (String permissionId : permissionList) {
                        if (StringUtils.isNumeric(permissionId)) {
                            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();

                            rolePermissionEntity.setRolePermissionRid(roleId);
                            rolePermissionEntity.setRolePermissionPid(Long.parseLong(permissionId));
                            rolePermissionEntity.setRolePermissionCreateuser(getUser().getUserId());
                            rolePermissionEntity.setRolePermissionStatus(
                                    (byte) PublicStatusEnum.ROLE_PERMISSION_IN_USE.getCode());
                            rolePermissionEntityList.add(rolePermissionEntity);
                        }
                    }
                }
            }
            roleService.editRolePermission(roleId, rolePermissionEntityList);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "editRolePermission success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description 获取权限列表
     *
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/queryAllPermission")
    public void queryAllPermission() throws PermissionException, IOException {
        userPermissionCheck("permission:query,permissionSelect:query");

        Map<String, Object> resultMap = new HashMap<>();
        List<PermissionEntity> allPermission = permissionService.queryAllPermission();

        resultMap.put("permissionList", allPermission);
        resultMap.put("msg", "query permission success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/queryAllRole")
    public void queryAllRole() throws IOException, PermissionException {
        userPermissionCheck("roleSelect:query");

        Map<String, Object> resultMap = new HashMap<>();
        List<RoleEntity> allRole = roleService.queryAllRole();

        resultMap.put("roleList", allRole);
        resultMap.put("msg", "query permission success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description 搜索人员
     *
     * @param filterString
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/queryFilterUser")
    public void queryFilterUser(String filterString) throws IOException, PermissionException {
        userPermissionCheck("userSelect:query");

        Map<String, Object> resultMap = new HashMap<>();
        List<UserEntity> userEntityList = userService.queryFilterUser(filterString);

        resultMap.put("users", userEntityList);
        resultMap.put("msg", "query user success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleId
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/queryPermissionByRoleId")
    public void queryPermissionByRoleId(Long roleId) throws IOException, PermissionException {
        userPermissionCheck("role:queryPermission");

        List<PermissionEntity> permissionEntityList = new ArrayList<>();

        if ((roleId != null) && (roleId != 0)) {
            permissionEntityList = permissionService.queryPermissionByRoleId(roleId);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("permissionList", permissionEntityList);
        resultMap.put("msg", "remove role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param request
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/queryRole")
    public void queryRole(HttpServletRequest request) throws IOException, PermissionException {
        userPermissionCheck("role:query");

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Long> searchMap = new HashMap<>();
        Long pageSize = 0L;
        Long currentPage = 0L;

        if (StringUtils.isNumeric(request.getParameter("role"))) {
            searchMap.put("role", Long.parseLong(request.getParameter("role")));
        }
        if (StringUtils.isNumeric(request.getParameter("permission"))) {
            searchMap.put("permission", Long.parseLong(request.getParameter("permission")));
        }
        if (StringUtils.isNumeric(request.getParameter("user"))) {
            searchMap.put("user", Long.parseLong(request.getParameter("user")));
        }
        if (StringUtils.isNumeric(request.getParameter("pageSize"))) {
            pageSize = Long.parseLong(request.getParameter("pageSize"));
            searchMap.put("pageSize", pageSize);
        } else {
            output(Response.fail(RequestFailCodeEnum.QUERY_ROLE_FIAL_PARAMETER_ERROR));
            return;
        }
        if (StringUtils.isNumeric(request.getParameter("currentPage"))) {
            currentPage = Long.parseLong(request.getParameter("currentPage"));
        } else {
            output(Response.fail(RequestFailCodeEnum.QUERY_ROLE_FIAL_PARAMETER_ERROR));
            return;
        }
        searchMap.put("startIndex", (currentPage - 1) * pageSize);

        int total = roleService.countRole(searchMap);
        List<RoleInfoEntity> allRole = new ArrayList<>();

        if (total > (currentPage - 1) * pageSize) {
            allRole = roleService.queryRole(searchMap);
        }
        resultMap.put("total", total);
        resultMap.put("currentPage", currentPage);
        resultMap.put("roleList", allRole);
        resultMap.put("msg", "query permission success");
        output(Response.ok(resultMap));
    }

    /**
     * Method description 删除权限
     *
     * @param permissionEntity
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/removePermission")
    public void removePermission(PermissionEntity permissionEntity) throws IOException, PermissionException {
        userPermissionCheck("permission:remove");

        Map<String, Object> resultMap = new HashMap<>();

        if (permissionEntity != null) {
            permissionEntity.setPermissionModifyuser(getUser().getUserId());
            permissionEntity.setPermissionModifydate(new Date());
            permissionService.removePermission(permissionEntity);
        }
        resultMap.put("msg", "remove permission success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description 删除角色
     *
     * @param roleId
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/removeRole")
    public void removeRole(Long roleId) throws IOException, PermissionException {
        userPermissionCheck("role:remove");
        if ((roleId != null) && (roleId != 0)) {
            Map<String, Long> conditionMap = new HashMap<>();

            conditionMap.put("roleId", roleId);
            conditionMap.put("modifyUser", getUser().getUserId());
            roleService.removeRole(conditionMap);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "remove role success ");
        output(Response.ok(resultMap));
    }

    /**
     * Method description
     *
     * @param roleId
     * @param userId
     * @throws IOException
     * @throws PermissionException
     */
    @RequestMapping("/removeUserFromRole")
    public void removeUserFromRole(Long roleId, Long userId) throws IOException, PermissionException {
        userPermissionCheck("role:removeUserFromRole");
        if ((roleId != null) && (roleId != 0) && (userId != null) && (userId != 0)) {
            UserRoleEntity userRoleEntity = new UserRoleEntity();

            userRoleEntity.setUserRoleUid(userId);
            userRoleEntity.setUserRoleRid(roleId);
            userRoleEntity.setUserRoleStatus((byte) PublicStatusEnum.USER_ROLE_NOT_IN_USE.getCode());
            userRoleEntity.setUserRoleModifyuser(getUser().getUserId());
            userRoleEntity.setUserRoleModifydate(new Date());
            roleService.removeUserFromRole(userRoleEntity);
        }

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("msg", "remove role success ");
        output(Response.ok(resultMap));
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
