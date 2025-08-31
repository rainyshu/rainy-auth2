package com.rainy.common.dao.jpa;

import com.rainy.common.entity.UserEntity;
import com.rainy.core.server.jpa.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseJpaRepository<UserEntity> {
}
