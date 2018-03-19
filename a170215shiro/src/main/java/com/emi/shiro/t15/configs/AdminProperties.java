package com.emi.shiro.t15.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by emi on 2017/2/17.
 *
 * notes:
 * ①：添加properties
 * ②：添加@ConfigurationProperties
 * ③：Application添加@EnableConfigurationProperties(AdminProperties.class)
 */
@ConfigurationProperties(prefix = "project.super.admin")
public class AdminProperties {

    private String name;

    private String pass;

    private String role;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
