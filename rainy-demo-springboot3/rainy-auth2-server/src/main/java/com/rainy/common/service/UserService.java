package com.rainy.common.service;

import com.rainy.common.controller.vo.UserVo;
import com.rainy.common.dao.UserDao;
import com.rainy.common.entity.UserEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseServiceImpl<UserEntity, UserVo> {

    @Autowired
    private UserDao userDao;

    @Override
    public IBaseDao<UserEntity> getBaseDao() {
        return userDao;
    }

}
