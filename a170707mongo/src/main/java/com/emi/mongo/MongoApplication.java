package com.emi.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@SpringBootApplication
@EnableMongoAuditing
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//Cannot determine embedded database driver class for database type NONE
public class MongoApplication {

	public static void main(String[] args) {
		System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>> 1 配置mongo java driver显示操作日志 但是不生效 <<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
		System.setProperty("DEBUG.MONGO", "true");
		System.setProperty("DB.TRACE", "true");


		SpringApplication.run(MongoApplication.class, args);
	}
}
