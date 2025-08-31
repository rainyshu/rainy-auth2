package com.rainy.biz.demo.condition;

import com.rainy.core.common.jdbc.JdbcCondition;

/**
 * @author Run.Shu
 */
public class RegionCondition extends JdbcCondition {

    private String regionCode;

    private String regionLevel;

    private String parentId;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(String regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
