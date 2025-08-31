package com.rainy.biz.demo.service;

import com.rainy.biz.demo.dao.DemoDao;
import com.rainy.biz.demo.dto.DemoDto;
import com.rainy.biz.demo.entity.DemoEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService extends BaseServiceImpl<DemoEntity, DemoDto> {

    @Autowired
    private DemoDao demoDao;

    @Override
    public IBaseDao<DemoEntity> getBaseDao() {
        return demoDao;
    }
}
