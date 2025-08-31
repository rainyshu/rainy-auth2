package com.rainy.core.server.impl;

import com.rainy.core.common.BaseDto;
import com.rainy.core.common.jdbc.JdbcCondition;
import com.rainy.core.common.jdbc.Page;
import com.rainy.core.entity.BaseEntity;
import com.rainy.core.server.IBaseDao;
import com.rainy.core.server.IBaseService;
import com.rainy.core.utils.JsonObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rainy.shu
 */
public abstract class BaseServiceImpl<E extends BaseEntity, DTO extends BaseDto> implements IBaseService<DTO>, SmartInitializingSingleton {

    private final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    private IBaseDao<E> baseDao;

    private Class<E> entityClazz;

    private Class<DTO> dtoClass;

    /**
     * 获取注入处理对象
     * @return 返回
     */
    public abstract IBaseDao<E> getBaseDao();

    @Override
    public DTO add(DTO dto) {
        logger.info("BaseServiceImpl add entry:{}", JsonObjectUtils.objToStr(dto));
        return add(dto, entityClazz);
    }

    @Override
    public DTO update(DTO dto) {
        logger.info("BaseServiceImpl update entry:{}", JsonObjectUtils.objToStr(dto));
        return update(dto, entityClazz);
    }

    @Override
    public <C extends JdbcCondition> List<DTO> getListByCondition(C condition) {
        logger.info("BaseServiceImpl getListByCondition entry:{}", JsonObjectUtils.objToStr(condition));
        List<E> list = baseDao.getListByCondition(condition, entityClazz);
        logger.info("BaseServiceImpl getListByCondition out:{}", JsonObjectUtils.objToStr(list));
        return toDto(list);
    }

    @Override
    public <C extends JdbcCondition> Page<DTO> getPageByCondition(C condition) {
        logger.info("BaseServiceImpl getPageByCondition entry:{}", JsonObjectUtils.objToStr(condition));
        Page<E> dbPage = baseDao.getPageByCondition(condition, entityClazz);
        Page<DTO> dtoPage = new Page<>();
        dtoPage.setPageNo(dbPage.getPageNo());
        dtoPage.setPageSize(dbPage.getPageSize());
        dtoPage.setData(JsonObjectUtils.objToList(dbPage.getData(), dtoClass));
        dtoPage.setTotalNum(dbPage.getTotalNum());
        logger.info("BaseServiceImpl getPageByCondition out:{}", JsonObjectUtils.objToStr(dtoPage));
        return dtoPage;
    }

    @Override
    public DTO delete(DTO dto) {
        logger.info("BaseServiceImpl delete entry:{}", JsonObjectUtils.objToStr(dto));
        E entity = toEntity(dto);
        entity = baseDao.delete(entity);
        logger.info("BaseServiceImpl delete out:{}", JsonObjectUtils.objToStr(entity));
        return dto;
    }

    @Override
    public DTO getById(Long id) {
        E entity = baseDao.getById(id);
        if (null == entity) {
            return null;
        }
        return toDto(entity);
    }

    /**
     * 保存实体
     * @param dto 参数
     * @param clazz 参数
     * @return 返回
     */
    private DTO add(DTO dto, Class<E> clazz) {
        E entity = toEntity(dto);
        entity = baseDao.add(entity);
        return toDto(entity);
    }

    /**
     * 保存实体
     * @param dto 参数
     * @param clazz 参数
     * @return 返回
     */
    private DTO update(DTO dto, Class<E> clazz) {
        E entity = toEntity(dto);
        entity = baseDao.update(entity);
        return toDto(entity);
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.baseDao = getBaseDao();
        this.entityClazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.dtoClass = (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    protected E toEntity(DTO dto) {
        E entity = null;
        try {
            Constructor<?> constructor = entityClazz.getConstructor();
            entity = (E) constructor.newInstance();
            BeanUtils.copyProperties(dto, entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    protected List<E> toEntity(List<DTO> dtos) {
        List<E> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(dtos)) {
            return list;
        }
        for (DTO dto : dtos) {
            list.add(toEntity(dto));
        }
        return list;
    }

    protected DTO toDto(E entity) {
        DTO dto = null;
        try {
            Constructor<?> constructor = dtoClass.getConstructor();
            dto = (DTO) constructor.newInstance();
            BeanUtils.copyProperties(entity, dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dto;
    }

    protected List<DTO> toDto(List<E> entityList) {
        List<DTO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return list;
        }
        for (E entity : entityList) {
            list.add(toDto(entity));
        }
        return list;
    }
}

