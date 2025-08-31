package com.rainy.core.server;

import com.rainy.core.common.jdbc.JdbcCondition;
import com.rainy.core.common.jdbc.Page;
import com.rainy.core.entity.BaseEntity;

import java.util.List;


/**
 * @author rainy.shu
 * @param <E>
 */
public interface IBaseDao<E extends BaseEntity> {

    /**
     * 保存
     * @param entity 参数
     * @return 返回
     */
    E add(E entity);

    /**
     * 查询
     * @param condition 条件
     * @param clazz 实体类Class
     * @return 返回
     */
    <C extends JdbcCondition> List<E> getListByCondition(C condition, Class<E> clazz);

    /**
     * 分页查询
     * @param condition 参数
     * @param clazz 参数
     * @return 返回
     */
    <C extends JdbcCondition> Page<E> getPageByCondition(C condition, Class<E> clazz);

    /**
     * 删除
     * @param entity 参数
     * @return 返回
     */
    E delete(E entity);

    /**
     * 删除
     * @param entity 参数
     * @return 返回
     */
    E deleteLogic(E entity);

    /**
     * 更新
     * @param entity 参数
     * @return 返回
     */
    E update(E entity);

    /**
     * 物理主键查询
     * @param id 物理主键查询
     * @return 返回值
     */
    E getById(Long id);
}

