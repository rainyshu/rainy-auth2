package com.rainy.common.condition;

import com.rainy.core.common.jdbc.JdbcCondition;

public class UserCondition extends JdbcCondition {

    private String username;

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
