package com.rainy.common.dao;

import com.rainy.common.dao.jpa.UserRepository;
import com.rainy.common.entity.UserEntity;
import com.rainy.core.server.impl.BaseDaoImpl;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao extends BaseDaoImpl<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseJpaRepository<UserEntity> getBaseJpaRepository() {
        return userRepository;
    }

}
