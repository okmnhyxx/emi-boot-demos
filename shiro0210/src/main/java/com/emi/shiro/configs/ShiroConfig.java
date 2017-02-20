package com.emi.shiro.configs;

import com.emi.shiro.component.MyShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by emi on 2017/2/10.
 */
@Configuration
public class ShiroConfig {

    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


    /**
     * 缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }

    /**
     * 自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     * @return
     */
    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCacheManager(ehCacheManager());
        return realm;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {

        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager(myShiroRealm());
        dwsm.setCacheManager(ehCacheManager());
        return dwsm;
    }

//    DefaultWebSessionManager

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
        sffb.setSecurityManager(securityManager());
        sffb.setLoginUrl("/alogin.html");
        sffb.setSuccessUrl("/success.html");
        sffb.setUnauthorizedUrl("/403.html");

        Map<String, String> filterChainDefinationMap = new LinkedHashMap<>();
//        filterChainDefinationMap.put("/user/*/edit/*", "authc,perms[user:edit]");
//        filterChainDefinationMap.put("/user/*/query/*", "authc,perms[user:query]");
//        filterChainDefinationMap.put("/query/b", "authc,perms[query]");
//        filterChainDefinationMap.put("/login*", "anon");
        filterChainDefinationMap.put("/index*", "authc");
        filterChainDefinationMap.put("/logout", "logout");
        filterChainDefinationMap.put("/**", "anon");
        sffb.setFilterChainDefinitionMap(filterChainDefinationMap);
        return sffb;
    }


    /**
     * 这是个DestructionAwareBeanPostProcessor的子类，负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     * @return
     */
    @Bean
//    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * shiro里实现的Advisor类，内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }



}
