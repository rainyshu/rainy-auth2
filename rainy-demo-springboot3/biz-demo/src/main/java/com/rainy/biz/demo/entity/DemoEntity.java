package com.rainy.biz.demo.entity;

import com.rainy.core.entity.BizEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "t_demo")
public class DemoEntity extends BizEntity {

    @Column(name = "demo_code")
    private String demoCode;

    @Column(name = "demo_name")
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
