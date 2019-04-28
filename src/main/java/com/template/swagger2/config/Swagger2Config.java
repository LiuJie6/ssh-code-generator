package com.template.swagger2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Project Name:electronic-invoice
 * File Name:Swagger2Config
 * Package Name:com.yk.invoice.swagger2.config
 * Date:2018/12/17
 * Author:zhangju
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.template.controller","com.template.model.api"})
public class Swagger2Config extends WebMvcConfigurationSupport {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
}
