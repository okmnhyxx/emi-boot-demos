package com.emi.shiro.t15.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emi on 2016/10/27.
 */
@Configuration
public class SystemProperties {

    @Value("${project.super.admin.name}")
    public String adminName;

    @Value("${project.super.admin.pass}")
    public int adminPass;



    public String getAdminName() {
        return adminName;
    }

    public int getAdminPass() {
        return adminPass;
    }

}
