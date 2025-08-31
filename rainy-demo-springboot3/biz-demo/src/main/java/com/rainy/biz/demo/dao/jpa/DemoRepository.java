package com.rainy.biz.demo.dao.jpa;

import com.rainy.biz.demo.entity.DemoEntity;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoRepository extends BaseJpaRepository<DemoEntity> {
}
