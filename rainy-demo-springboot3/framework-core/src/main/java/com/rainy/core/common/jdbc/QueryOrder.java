package com.rainy.core.common.jdbc;

/**
 * @author rainy.shu
 */
public class QueryOrder {

    /**
     * 实体类字段名称
     */
    private String fieldName;

    /**
     * 操作类型枚举
     */
    private OrderJpaType orderJpaType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public OrderJpaType getOrderJpaType() {
        return orderJpaType;
    }

    public void setOrderJpaType(OrderJpaType orderJpaType) {
        this.orderJpaType = orderJpaType;
    }

    public QueryOrder(String fieldName, OrderJpaType orderJpaType) {
        this.fieldName = fieldName;
        this.orderJpaType = orderJpaType;
    }
}

