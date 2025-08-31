package com.rainy.core.server;

import com.rainy.core.common.BaseDto;
import com.rainy.core.common.jdbc.JdbcCondition;
import com.rainy.core.common.jdbc.Page;

import java.util.List;

/**
 * @author rainy.shu
 */
public interface IBaseService<DTO extends BaseDto> {
    /**
     * 保存
     * @param dto 参数
     * @return 返回
     */
    DTO add(DTO dto);

    /**
     * 查询
     * @param condition 条件
     * @return 返回
     */
    <C extends JdbcCondition> List<DTO> getListByCondition(C condition);

    /**
     * 查询
     * @param condition 条件
     * @return 返回
     */
    <C extends JdbcCondition> Page<DTO> getPageByCondition(C condition);

    /**
     * 删除对象
     * @param dto 参数
     * @return 返回
     */
    DTO delete(DTO dto);

    /**
     * 更新
     * @param dto 参数
     * @return 返回
     */
    DTO update(DTO dto);

    /**
     * 物理主键查询
     * @param id 物理主键查询
     * @return 返回值
     */
    DTO getById(Long id);
}

