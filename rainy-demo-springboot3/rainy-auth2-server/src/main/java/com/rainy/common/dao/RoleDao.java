package com.rainy.common.dao;

import com.rainy.common.dao.jpa.RoleRepository;
import com.rainy.common.entity.RoleEntity;
import com.rainy.core.server.impl.BaseDaoImpl;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleDao extends BaseDaoImpl<RoleEntity> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public BaseJpaRepository<RoleEntity> getBaseJpaRepository() {
        return roleRepository;
    }

}
