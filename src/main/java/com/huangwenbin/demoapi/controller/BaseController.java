package com.huangwenbin.demoapi.controller;

import com.huangwenbin.demoapi.common.JsonUtils;
import com.huangwenbin.demoapi.common.Response;
import com.huangwenbin.demoapi.common.StringUtils;
import com.huangwenbin.demoapi.common.UserException.PermissionException;
import com.huangwenbin.demoapi.entity.userentity.PermissionUserEntity;
import com.huangwenbin.demoapi.enumeration.RequestFailCodeEnum;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class description
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/23
 */
@RestController
public class BaseController {

    /**
     * Field description
     */
    private static final Log logger = LogFactory.getLog(BaseController.class);

    /**
     * Field description
     */
    private HttpServletRequest request;

    /**
     * Field description
     */
    private HttpServletResponse response;

    /**
     * Field description
     */
    private PermissionUserEntity user;

    /**
     * Method description
     *
     * @param e
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        try {
            String controllerName = "";
            String methodName = "";
            Long userId = 0L;

            if ((this.request != null) && !StringUtils.nullOrEmpty(this.request.getRequestURL().toString())) {
                String[] url = this.request.getRequestURL().toString().split("/");

                if ((url != null) && (url.length >= 2)) {
                    controllerName = url[url.length - 2];
                    methodName = url[url.length - 1];
                }
            }

            PermissionUserEntity permissionUserEntity = getPermissionUserEntity();

            if ((permissionUserEntity != null) && (permissionUserEntity.getUserId() != 0)) {
                userId = permissionUserEntity.getUserId();
            }

            Map<String, Object> errorMsgMap = new HashMap<>();

            errorMsgMap.put("controllerName", controllerName);
            errorMsgMap.put("methodName", methodName);
            errorMsgMap.put("userId", userId);
            errorMsgMap.put("errorMsg", e.getMessage());
            logger.error(errorMsgMap, e);
        } catch (Exception ex) {
        }
    }

    /**
     * Method description
     *
     * @param response
     * @throws IOException
     */
    public void output(Response response) throws IOException {
        this.response.setHeader("Content-type", "text/html;charset=UTF-8");
        this.response.setCharacterEncoding("UTF-8");

        PrintWriter out = this.response.getWriter();
        String callBack = this.request.getParameter("callback");    // 客户端请求参数

        if (!StringUtils.nullOrEmpty(callBack)) {

            // 返回jsonp格式数据
            out.println(callBack + "(" + JSONObject.fromObject(response).toString(1, 1) + ")");
        } else {

            // 返回json格式数据
            out.println(JSONObject.fromObject(response));
        }

        out.flush();
        out.close();
    }

    /**
     * Method description
     *
     * @return
     * @throws IOException
     * @throws PermissionException
     */
    public boolean userLoginCheck() throws IOException, PermissionException {
        return userPermissionCheck("");
    }

    /**
     * Method description
     *
     * @param permissionNames
     * @return
     * @throws IOException
     * @throws PermissionException
     */
    public boolean userPermissionCheck(String permissionNames) throws IOException, PermissionException {
        PermissionUserEntity permissionUserEntity = getPermissionUserEntity();

        if ((permissionUserEntity != null)
                && (permissionUserEntity.getUserId() != null)
                && (permissionUserEntity.getUserId() != 0)) {
            if ((permissionNames == null) || (permissionNames.length() == 0)) {
                return true;
            } else {
                String[] roles = permissionNames.split(",");
                List<String> permissionStr = permissionUserEntity.getPerminsStrlist();
                boolean flag = false;

                if ((permissionStr != null) && (permissionStr.size() > 0)) {
                    for (String str : roles) {
                        if (permissionStr.contains(str)) {
                            flag = true;

                            continue;
                        }
                    }
                }

                if (!flag) {
                    output(Response.fail(RequestFailCodeEnum.PERMISSION_FIAL_NO_PERMISSION));

                    throw new PermissionException();
                }

                return flag;
            }
        }

        output(Response.fail(RequestFailCodeEnum.PERMISSION_FIAL_NO_LOGIN));

        throw new PermissionException();
    }

    /**
     * Method description
     *
     * @return
     */
    protected PermissionUserEntity getPermissionUserEntity() {
        PermissionUserEntity permissionUserEntity = null;

        if ((this.request != null) && !StringUtils.nullOrEmpty(this.request.getParameter("uuid"))) {
            String uuid = this.request.getParameter("uuid");
            HttpSession session = this.request.getSession();
            if (session.getAttribute(uuid) != null) {
                permissionUserEntity = JsonUtils.toObject(session.getAttribute(uuid).toString(),
                        PermissionUserEntity.class);
            }
        }
        return permissionUserEntity;
    }

    /**
     * Method description
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Method description
     *
     * @param request
     */
    @Resource
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Method description
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    /**
     * Method description
     *
     * @param response
     */
    @Resource
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Method description
     *
     * @return
     */
    public PermissionUserEntity getUser() {
        return getPermissionUserEntity();
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
