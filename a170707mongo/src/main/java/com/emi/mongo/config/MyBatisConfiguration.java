package com.emi.mongo.config;

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
 * https://my.oschina.net/wangnian/blog/667764
 */
@Configuration
public class MyBatisConfiguration {

//    @Autowired
//    private DataSource dataSource;

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

//        @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactoryBean() {
//        SqlSessionFactoryBean sqlsession = new SqlSessionFactoryBean();
//        sqlsession.setDataSource(dataSource);
//        sqlsession.setTypeAliasesPackage("com.fengchao.model");//扫描entity包 使用别名
//        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
//        configuration.setUseGeneratedKeys(true);//使用jdbc的getGeneratedKeys获取数据库自增主键值
//        configuration.setUseColumnLabel(true);//使用列别名替换列名 select user as User
//        configuration.setMapUnderscoreToCamelCase(true);//-自动使用驼峰命名属性映射字段   userId    user_id
//        sqlsession.setConfiguration(configuration);
//        sqlsession.setFailFast(true);
//        //添加XML目录
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        try {
//            sqlsession.setMapperLocations(resolver.getResources("classpath:mybatis-mapper/*.xml"));
//            return sqlsession.getObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//    @Bean
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
