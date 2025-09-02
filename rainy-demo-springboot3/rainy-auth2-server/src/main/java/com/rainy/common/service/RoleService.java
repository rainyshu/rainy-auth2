package com.rainy.common.service;

import com.rainy.common.condition.RoleCondition;
import com.rainy.common.controller.vo.RoleVo;
import com.rainy.common.dao.RoleDao;
import com.rainy.common.entity.RoleEntity;
import com.rainy.core.exception.BusinessExceptionFactory;
import com.rainy.core.exception.ValidRuntimeException;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends BaseServiceImpl<RoleEntity, RoleVo> {

    @Autowired
    private RoleDao roleDao;

    @Override
    public IBaseDao<RoleEntity> getBaseDao() {
        return roleDao;
    }

    @Override
    public void valid(RoleVo dto) {
        //新增
        if (null == dto.getId()) {
            RoleCondition roleCondition = new RoleCondition();
            roleCondition.setCode(dto.getCode());
            List<RoleEntity> dbs = roleDao.getListByCondition(roleCondition, RoleEntity.class);
            if (!CollectionUtils.isEmpty(dbs)) {
                throw new ValidRuntimeException("编码已存在");
            }
        }
        //更新
        if (null != dto.getId()) {
            RoleCondition roleCondition = new RoleCondition();
            roleCondition.setCode(dto.getCode());
            List<RoleEntity> dbs = roleDao.getListByCondition(roleCondition, RoleEntity.class);
            dbs = dbs.stream().filter(x -> !x.getId().equals(dto.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(dbs)) {
                throw new ValidRuntimeException("编码已存在");
            }
        }
    }
}
