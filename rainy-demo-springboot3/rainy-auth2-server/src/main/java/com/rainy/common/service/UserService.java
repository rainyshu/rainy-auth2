package com.rainy.common.service;

import com.rainy.common.condition.UserCondition;
import com.rainy.common.controller.vo.UserVo;
import com.rainy.common.dao.UserDao;
import com.rainy.common.entity.UserEntity;
import com.rainy.core.exception.ValidRuntimeException;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseServiceImpl<UserEntity, UserVo> {

    @Autowired
    private UserDao userDao;

    @Override
    public IBaseDao<UserEntity> getBaseDao() {
        return userDao;
    }

    @Override
    public void valid(UserVo dto) {
        //新增
        if (null == dto.getId()) {
            UserCondition userCondition = new UserCondition();
            userCondition.setUsername(dto.getUsername());
            List<UserEntity> dbs = userDao.getListByCondition(userCondition, UserEntity.class);
            if (!CollectionUtils.isEmpty(dbs)) {
                throw new ValidRuntimeException("用户名已存在");
            }
        }
        //更新
        if (null != dto.getId()) {
            UserCondition userCondition = new UserCondition();
            userCondition.setUsername(dto.getUsername());
            List<UserEntity> dbs = userDao.getListByCondition(userCondition, UserEntity.class);
            dbs = dbs.stream().filter(x -> !x.getId().equals(dto.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dbs)) {
                throw new ValidRuntimeException("用户名已存在");
            }
        }
    }

    public void resetPassword(Long id, String password) {
        UserVo userDb = getById(id);
        userDb.setPassword(password);
        update(userDb);
    }
}
