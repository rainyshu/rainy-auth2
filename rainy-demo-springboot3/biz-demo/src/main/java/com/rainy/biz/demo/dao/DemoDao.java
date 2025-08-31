package com.rainy.biz.demo.dao;

import com.rainy.biz.demo.dao.jpa.DemoRepository;
import com.rainy.biz.demo.entity.DemoEntity;
import com.rainy.core.server.impl.BaseDaoImpl;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemoDao extends BaseDaoImpl<DemoEntity> {

    @Autowired
    private DemoRepository demoRepository;

    @Override
    public BaseJpaRepository<DemoEntity> getBaseJpaRepository() {
        return demoRepository;
    }
}
