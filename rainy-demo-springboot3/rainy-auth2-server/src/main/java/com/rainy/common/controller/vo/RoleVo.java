package com.rainy.common.controller.vo;

import com.rainy.core.common.BaseDto;

public class RoleVo extends BaseDto {

    private String code;

    private String name;

    private Boolean enable;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
