package com.emi.shiro.t15.component;

import com.emi.shiro.t15.service.PermissionService;
import com.emi.shiro.t15.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by emi on 2017/2/16.
 */
@Component
@Configuration
public class MyInit implements CommandLineRunner {

    @Autowired
    private PermissionService resourceService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... strings) throws Exception {

        resourceService.ensureResourceInit();
        userService.ensureAdminInit();
    }
}
