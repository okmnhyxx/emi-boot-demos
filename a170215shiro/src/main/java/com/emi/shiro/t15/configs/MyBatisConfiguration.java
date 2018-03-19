package com.emi.shiro.t15.configs;

import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by emi on 2016/11/4.
 * 参考自https://yq.aliyun.com/articles/5831
 * http://www.open-open.com/lib/view/open1453349961745.html
 */
@Configuration
public class MyBatisConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);
    @Bean
    public PageHelper pageHelper() {
        //logger.info("注册MyBatis分页插件PageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("returnPageInfo", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
