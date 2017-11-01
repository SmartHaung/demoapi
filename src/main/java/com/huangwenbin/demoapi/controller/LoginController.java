package com.huangwenbin.demoapi.controller;

import com.huangwenbin.demoapi.common.EncryptUtils;
import com.huangwenbin.demoapi.common.JsonUtils;
import com.huangwenbin.demoapi.common.Response;
import com.huangwenbin.demoapi.common.StringUtils;
import com.huangwenbin.demoapi.entity.generateentity.PermissionEntity;
import com.huangwenbin.demoapi.entity.generateentity.RoleEntity;
import com.huangwenbin.demoapi.entity.generateentity.UserEntity;
import com.huangwenbin.demoapi.entity.userentity.PermissionUserEntity;
import com.huangwenbin.demoapi.enumeration.RequestFailCodeEnum;
import com.huangwenbin.demoapi.service.PermissionService;
import com.huangwenbin.demoapi.service.RoleService;
import com.huangwenbin.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Class description
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/23
 */
@RestController
@RequestMapping("/user")
public class LoginController extends BaseController {

    /**
     * Field description
     */
    @Autowired
    private UserService userService;

    /**
     * Field description
     */
    @Autowired
    private RoleService roleService;

    /**
     * Field description
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * Method description
     *
     * @param account  账号
     * @param password 密码
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/login")
    public void login(String account, String password) throws IOException, NoSuchAlgorithmException {
        if (StringUtils.nullOrEmpty(account)) {
            output(Response.fail(RequestFailCodeEnum.LOGIN_FIAL_ACCOUNT_CAN_NOT_BE_EMPTY));
            return;
        }
        if (StringUtils.nullOrEmpty(password)) {
            output(Response.fail(RequestFailCodeEnum.LOGIN_FIAL_PASSWORD_CAN_NOT_BE_EMPTY));
            return;
        }

        List<UserEntity> userEntityList = userService.queryUserByAccount(account);
        if (userEntityList == null || userEntityList.size() == 0) {
            output(Response.fail(RequestFailCodeEnum.PERMISSION_FIAL_NO_SUCH_ACCOUNT));
        }
        UserEntity userEntity = new UserEntity();
        for (UserEntity user : userEntityList) {
            if (EncryptUtils.EncoderByMd5(password).equals(user.getUserPassword())) {
                userEntity = user;
                userEntity.setUserPassword("");
                break;
            }
        }
        if (userEntity != null && userEntity.getUserId() != null && userEntity.getUserId() != 0) {
            Map<String, Object> resultMap = new HashMap<>();
            String uuid = UUID.randomUUID().toString();
            HttpSession session = getRequest().getSession();
            List<RoleEntity> roleEntityList = roleService.queryRoleByUid(userEntity.getUserId());
            List<PermissionEntity> permissionEntityList =
                    permissionService.queryPermissionByUid(userEntity.getUserId());
            PermissionUserEntity permissionUserEntity = JsonUtils.toObject(JsonUtils.toJson(userEntity),
                    PermissionUserEntity.class);

            if ((roleEntityList != null) && (roleEntityList.size() > 0)) {
                List<String> roleStringList = new ArrayList<>();

                for (RoleEntity entity : roleEntityList) {
                    roleStringList.add(entity.getRoleName());
                }
                permissionUserEntity.setRoleStrlist(roleStringList);
            }
            if ((permissionEntityList != null) && (permissionEntityList.size() > 0)) {
                List<String> permissionStringList = new ArrayList<>();

                for (PermissionEntity entity : permissionEntityList) {
                    permissionStringList.add(entity.getPermissionName());
                }
                permissionUserEntity.setPerminsStrlist(permissionStringList);
            }
            if (session.getAttribute("userId" + permissionUserEntity.getUserId().toString()) != null) {
                uuid = (String) session.getAttribute("userId" + permissionUserEntity.getUserId().toString());
            } else {
                session.setAttribute("userId" + permissionUserEntity.getUserId().toString(), uuid);
            }
            resultMap.put("uuid", uuid);
            resultMap.put("userInfo", permissionUserEntity);
            resultMap.put("msg", "login success");
            session.setAttribute(uuid, JsonUtils.toJson(permissionUserEntity));
            output(Response.ok(resultMap));
        } else {
            output(Response.fail(RequestFailCodeEnum.PERMISSION_FIAL_PASSWORD_ERROR));
        }
    }

    /**
     * Method description
     *
     * @param uuid
     * @throws IOException
     */
    @RequestMapping("/loginOut")
    public void loginOut(String uuid) throws IOException {
        PermissionUserEntity permissionUserEntity = getPermissionUserEntity();
        HttpSession session = getRequest().getSession();

        if ((permissionUserEntity != null)
                && (permissionUserEntity.getUserId() != null)
                && (permissionUserEntity.getUserId() != 0)) {
            session.removeAttribute("userId" + permissionUserEntity.getUserId().toString());
            session.removeAttribute(uuid);
        }
        output(Response.ok("登出成功"));
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
