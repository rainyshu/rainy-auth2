package com.rainy.common.entity;

import com.rainy.core.entity.BizEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "sys_user")
public class UserEntity extends BizEntity {

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(nullable = false, insertable = true, updatable = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
