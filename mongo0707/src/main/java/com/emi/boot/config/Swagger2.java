package com.emi.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by emi on 2016/10/31.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .pathMapping("/parking")
                .groupName("parking")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.emi.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("服务器ip：39.108.92.238；\r\n" +
                        "服务器log：/var/log/parking； \r\n" +
                        "源代码：https://github.com/okmnhyxx/parking.git"
                        )
                .termsOfServiceUrl("http://localhost:8010/parking/swagger-ui.html")
                .contact(new Contact("emi", "", "okmnhyxx@163.com"))
                .version("1.0")
                .build();
    }
}
