package com.rainy.biz.demo.dao;

import com.rainy.biz.demo.dao.jpa.RegionRepository;
import com.rainy.biz.demo.entity.RegionEntity;
import com.rainy.core.server.impl.BaseDaoImpl;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Run.Shu
 */
@Component
public class RegionDao extends BaseDaoImpl<RegionEntity> {

    @Autowired
    private RegionRepository regionRepository;

    @Override
    public BaseJpaRepository<RegionEntity> getBaseJpaRepository() {
        return regionRepository;
    }
}
