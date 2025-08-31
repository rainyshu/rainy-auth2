package com.rainy.core.common;

import com.rainy.core.exception.CodeBusinessException;

import java.io.Serializable;

/**
 * @author rainy.shu
 * @param <T>
 */
public class Result<T> implements Serializable {

    /**
     * 000000表示成功，其他表示失败
     */
    private String code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> toSuccess(T data) {
        Result<T> result = new Result<T>();
        result.setCode(CodeBusinessException.B_SUCCESS);
        result.setMsg("");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> toFail(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(CodeBusinessException.B_FAIL);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}

