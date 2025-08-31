package com.rainy.biz.demo.entity;

import com.rainy.core.entity.BizEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "mall_f_region")
public class RegionEntity extends BizEntity {

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "region_level")
    private String regionLevel;

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(String regionLevel) {
        this.regionLevel = regionLevel;
    }
}
