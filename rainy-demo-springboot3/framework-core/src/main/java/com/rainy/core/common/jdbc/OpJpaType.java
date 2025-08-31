package com.rainy.core.common.jdbc;

/**
 * @author rainy.shu
 */
public enum OpJpaType {
    /**
     * 等于
     */
    EQUAL,
    /**
     * 不等于
     */
    NOT_EQUAL,
    /**
     * 包含字符串（全like查询value不拼接%，前后like拼接%）
     */
    LIKE,
    /**
     * 不包含字符串（全like查询value不拼接%，前后like拼接%）
     */
    NOT_LIKE,
    /**
     * 集合中
     */
    IN,
    /**
     * 不在集合中
     */
    NOT_IN,
    /**
     * 为空
     */
    NULL,
    /**
     * 不为空
     */
    NOT_NULL,
    /**
     * 小于等于（支持Number、Date、String）
     */
    LESS_THAN_OR_EQUAL_TO,
    /**
     * 小于（支持Number、Date、String）
     */
    LESS_THAN,
    /**
     * 大于等于（支持Number、Date、String）
     */
    GREATER_THAN_OR_EQUAL_TO,
    /**
     * 大于（支持Number、Date、String）
     */
    GREATER_THAN,
    /**
     * 为真（boolean类型）
     */
    TRUE,
    /**
     * 不为真（boolean类型）
     */
    FALSE
}

