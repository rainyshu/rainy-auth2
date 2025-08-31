package com.rainy.core.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * @author rainy.shu
 */
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BizEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "record_version", nullable = false, columnDefinition = "int(10) DEFAULT 0")
    private Long recordVersion;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "int(10) DEFAULT 0")
    private Integer isDeleted;

    /**
     * 创建人
     */
    @Column(name = "create_user", nullable = false, columnDefinition = "varchar(50) DEFAULT ''")
    private String createUser;
    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "update_user", nullable = false, columnDefinition = "varchar(50) DEFAULT ''")
    private String updateUser;

    /**
     * 修改时间
     */
    @Column(name = "update_time", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP()")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Long recordVersion) {
        this.recordVersion = recordVersion;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

