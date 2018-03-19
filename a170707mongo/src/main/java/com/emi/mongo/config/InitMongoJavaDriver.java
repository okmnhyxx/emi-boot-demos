package com.emi.mongo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emi on 2017/7/13.
 */
@Configuration
public class InitMongoJavaDriver implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>> 2 配置mongo java driver显示操作日志 但是不生效  <<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
        System.setProperty("DEBUG.MONGO", "true");
        System.setProperty("DB.TRACE", "true");
    }
}
