package com.emi.shiro;

import com.emi.shiro.configs.AdminProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@MapperScan("com.emi.shiro.repository.mapper")
@EnableConfigurationProperties(AdminProperties.class)
@SpringBootApplication
public class Shiro0215Application {

	public static void main(String[] args) {
		SpringApplication.run(Shiro0215Application.class, args);
	}
}
