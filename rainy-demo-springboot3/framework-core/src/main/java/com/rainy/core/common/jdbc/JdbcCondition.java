package com.rainy.core.common.jdbc;

import com.rainy.core.common.Condition;

import java.util.List;

/**
 * @author rainy.shu
 */
public class JdbcCondition extends Condition {

    /**
     * 每页大小
     */
    private Integer pageSize = 20;

    /**
     * 页数
     */
    private Integer pageNo = 1;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 复杂字段操作参数
     */
    private List<QueryParam> queryParams;

    /**
     * 排序字段
     */
    private List<QueryOrder> queryOrders;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getIsDeleted() {
        return 0;
    }

    public List<QueryParam> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<QueryParam> queryParams) {
        this.queryParams = queryParams;
    }

    public List<QueryOrder> getQueryOrders() {
        return queryOrders;
    }

    public void setQueryOrders(List<QueryOrder> queryOrders) {
        this.queryOrders = queryOrders;
    }
}

