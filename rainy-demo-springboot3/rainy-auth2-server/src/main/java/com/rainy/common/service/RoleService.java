package com.rainy.common.service;

import com.rainy.common.controller.vo.RoleVo;
import com.rainy.common.dao.RoleDao;
import com.rainy.common.entity.RoleEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseServiceImpl<RoleEntity, RoleVo> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public IBaseDao<RoleEntity> getBaseDao() {
        return roleDao;
    }

}
