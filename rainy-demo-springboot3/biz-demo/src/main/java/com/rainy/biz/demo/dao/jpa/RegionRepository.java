package com.rainy.biz.demo.dao.jpa;

import com.rainy.biz.demo.entity.RegionEntity;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Run.Shu
 */
@Repository
public interface RegionRepository extends BaseJpaRepository<RegionEntity> {
}
