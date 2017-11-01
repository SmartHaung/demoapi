package com.huangwenbin.demoapi.service;

import java.util.List;

import com.huangwenbin.demoapi.entity.generateentity.UserEntity;

/**
 * Interface description
 *
 * @author huangwenbin
 * @version 1.0.0.0, 17/10/30
 */
public interface UserService {

    /**
     * Method description
     *
     * @param filterString
     * @return
     */
    List<UserEntity> queryFilterUser(String filterString);

    /**
     * Method description
     *
     * @param account
     * @return
     */
    List<UserEntity> queryUserByAccount(String account);
}


//~ Formatted by Jindent --- http://www.jindent.com
