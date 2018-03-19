package com.emi.mongo.config;

import org.springframework.context.annotation.Configuration;

/**
 * Created by emi on 2017/7/13.
 */
@Configuration
public class ConfigBeans {

    /**
     * 3 配置mongo java driver显示操作日志 但是不生效
     */
//    @Bean
//    public MethodInvokingFactoryBean fetchMIFB() {
//        MethodInvokingFactoryBean m = new MethodInvokingFactoryBean();
//        m.setTargetClass(System.class);
//        m.setTargetMethod("setProperty");
//        m.setArguments(new Object[] {"DEBUG.MONGO", true});
//        m.setArguments(new Object[] {"DB.TRACE", true});
//        return m;
//    }


}
