package com.rainy.core.server;

import com.rainy.core.common.BaseDto;
import com.rainy.core.common.jdbc.JdbcCondition;

import java.util.List;

/**
 * @author rainy.shu
 */
public interface IBaseController<DTO extends BaseDto> {

    /**
     * 新增
     */
    DTO add(DTO dto);

    /**
     * 修改
     */
    DTO update(DTO dto);

    /**
     * 列表查询
     */
    <C extends JdbcCondition> List<DTO> getListByCondition(C condition);

    /**
     * 删除
     */
    DTO delete(DTO dto);

    /**
     * 物理主键查询
     * @param id 物理主键查询
     * @return 返回值
     */
    DTO getById(Long id);

}
