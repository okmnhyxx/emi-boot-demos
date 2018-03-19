package com.emi.shiro.t15;

import com.emi.shiro.t15.component.bind.method.CurrentUserMethodArgumentResolver;
import com.emi.shiro.t15.configs.AdminProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@EnableJpaAuditing
@MapperScan("com.emi.shiro.t15.repository.mapper")
//@ServletComponentScan
@EnableConfigurationProperties(AdminProperties.class)
@SpringBootApplication
public class Shiro0215Application  extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Shiro0215Application.class, args);
	}


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		//super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new CurrentUserMethodArgumentResolver());

	}

}
