package com.huangwenbin.demoapi.common;


import com.huangwenbin.demoapi.enumeration.RequestFailCodeEnum;

import java.util.HashMap;
import java.util.Map;

public class Response extends HashMap<String, Object> {
    private static int SUCCESS = 1001;
    private static int ERROR = 1002;
    private static int FAIL = 1003;

    public static Response ok() {
        Response response = new Response();
        response.put("code", SUCCESS);
        return response;
    }

    public static Response ok(String msg) {
        Response response = ok();
        response.put("msg", msg);
        return response;
    }

    public static Response ok(Map<String, Object> map) {
        Response response = ok();
        response.putAll(map);
        return response;
    }

    public static Response fail() {
        Response response = new Response();
        response.put("code", FAIL);
        return response;
    }

    public static Response fail(RequestFailCodeEnum failCodeEnum) {
        return Response.fail().put("failCode", failCodeEnum.getCode()).put("failMsg", failCodeEnum.getMsg());
    }

    public static Response fail(String msg) {
        return Response.fail().put("msg", msg);
    }

    public static Response fail(Map<String, Object> map) {
        Response response = fail();
        response.putAll(map);
        return response;
    }

    public static Response error() {
        Response response = new Response().put("code", ERROR);
        return response;
    }

    public static Response error(String msg) {
        Response response = fail().put("msg", msg);
        return response;
    }

    public static Response error(Map<String, Object> map) {
        Response response = fail();
        response.putAll(map);
        return response;
    }

    public Response put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
