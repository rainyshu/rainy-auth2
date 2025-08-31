package com.rainy.common.dao.jpa;

import com.rainy.common.entity.RoleEntity;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseJpaRepository<RoleEntity> {
}
