package com.example.studentmanagementsystem.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置信息
 * springdoc.api-docs.enabled=true
 * springdoc.swagger-ui.path=/swagger-ui.html
 * springdoc.packages-to-scan=com.example.studentmanagementsystem.controller
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customConfig()
    {
        return new OpenAPI().info(new Info().title("学生管理系统 API 文档")
                .description("基于 Spring Boot + JPA 的学生管理系统接口")
                .version("v1.0.0")
                .contact(new Contact().name("Instra").email("3026164589@qq.com").url("https://github.com/instra15/StudentManagementSystem.git")));



    }



}
