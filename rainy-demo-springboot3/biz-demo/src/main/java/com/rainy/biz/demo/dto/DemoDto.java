package com.rainy.biz.demo.dto;

import com.rainy.core.common.BaseDto;

public class DemoDto extends BaseDto {

    private String demoCode;

    private String demoName;

    public String getDemoCode() {
        return demoCode;
    }

    public void setDemoCode(String demoCode) {
        this.demoCode = demoCode;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }
}
