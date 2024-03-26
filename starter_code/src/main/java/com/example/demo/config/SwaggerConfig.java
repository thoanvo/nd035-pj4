package com.example.demo.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    // ApiInfo contains custom information about API:
    private ApiInfo apiInfo() {
	return new ApiInfo("THOANVTT API", "nd035-c4-Security-and-DevOps", "1.0.0", "http://localhost:8080",
		new Contact("ThoanVTT", "www.abc.com", "thoanvtt@abc.com"), "License of API", "API license URL",
		Collections.emptyList());
    }
}