package com.rainy.core.server.jpa;

import com.rainy.core.entity.BizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author rainy.shu
 * @param <E>
 */
public interface BaseJpaRepository<E extends BizEntity> extends JpaRepository<E, Long>, JpaSpecificationExecutor<E> {
}

