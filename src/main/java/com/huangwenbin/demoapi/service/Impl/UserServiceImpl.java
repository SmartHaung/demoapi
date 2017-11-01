package com.huangwenbin.demoapi.service.Impl;

import com.huangwenbin.demoapi.entity.generateentity.UserEntity;
import com.huangwenbin.demoapi.mapper.UserMapper;
import com.huangwenbin.demoapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userEntityMapper;

    @Override
    public List<UserEntity> queryUserByAccount(String account) {
        return userEntityMapper.queryUserByAccount(account);
    }

    @Override
    public List<UserEntity> queryFilterUser(String filterString) {
        List<UserEntity> resList = new ArrayList<>();
        if (filterString != null) {
            return userEntityMapper.queryFilterUser(filterString);
        }
        return resList;
    }
}
