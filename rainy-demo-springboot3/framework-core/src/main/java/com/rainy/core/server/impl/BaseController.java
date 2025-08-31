package com.rainy.core.server.impl;

import com.rainy.core.common.BaseDto;
import com.rainy.core.common.jdbc.JdbcCondition;
import com.rainy.core.server.IBaseController;
import com.rainy.core.server.IBaseService;
import com.rainy.core.utils.JsonObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.List;

/**
 * @author rainy.shu
 */
public abstract class BaseController<DTO extends BaseDto> implements IBaseController<DTO>, SmartInitializingSingleton {

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private IBaseService<DTO> baseService;

    /**
     * 获取注入处理对象
     * @return 返回
     */
    public abstract IBaseService<DTO> getBaseService();

    @Override
    public DTO add(DTO dto) {
        logger.info("BaseController add entry:{}", JsonObjectUtils.objToStr(dto));
        return baseService.add(dto);
    }

    @Override
    public DTO update(DTO dto) {
        logger.info("BaseController update entry:{}", JsonObjectUtils.objToStr(dto));
        return baseService.update(dto);
    }

    @Override
    public <C extends JdbcCondition> List<DTO> getListByCondition(C condition) {
        logger.info("BaseController getListByCondition entry:{}", JsonObjectUtils.objToStr(condition));
        return baseService.getListByCondition(condition);
    }

    @Override
    public DTO delete(DTO dto) {
        logger.info("BaseController delete entry:{}", JsonObjectUtils.objToStr(dto));
        return baseService.delete(dto);
    }

    @Override
    public DTO getById(Long id) {
        return baseService.getById(id);
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.baseService = getBaseService();
    }

}

