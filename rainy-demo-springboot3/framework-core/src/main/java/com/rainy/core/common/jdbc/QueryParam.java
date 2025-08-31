package com.rainy.core.common.jdbc;

/**
 * @author rainy.shu
 */
public class QueryParam {

    /**
     * 实体类字段名称
     */
    private String fieldName;

    /**
     * 操作类型枚举
     */
    private OpJpaType opJpaType;

    /**
     * 查询值（包装数据类型）
     */
    private Object value;

    /**
     * 查询连接符（and或or 默认and）
     */
    private String connector;

    /**
     * 构造函数
     * @param fieldName 参数
     * @param opJpaType 参数
     * @param value 参数
     */
    public QueryParam(String fieldName, OpJpaType opJpaType, Object value) {
        super();
        this.fieldName = fieldName;
        this.opJpaType = opJpaType;
        this.value = value;
    }

    /**
     * 构造函数
     * @param fieldName 参数
     * @param opJpaType 参数
     * @param value 参数
     * @param connector 参数
     */
    public QueryParam(String fieldName, OpJpaType opJpaType, Object value, String connector) {
        super();
        this.fieldName = fieldName;
        this.opJpaType = opJpaType;
        this.value = value;
        this.connector = connector;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public OpJpaType getOpJpaType() {
        return opJpaType;
    }

    public void setOpJpaType(OpJpaType opJpaType) {
        this.opJpaType = opJpaType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }
}

