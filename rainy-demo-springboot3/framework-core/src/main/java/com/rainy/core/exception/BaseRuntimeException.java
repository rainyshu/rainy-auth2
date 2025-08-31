package com.rainy.core.exception;

/**
 * @author rainy.shu
 */
public class BaseRuntimeException extends RuntimeException {

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BaseRuntimeException(String code, String desc) {
        super(desc);
        this.code = code;
        this.desc = desc;
    }
}

