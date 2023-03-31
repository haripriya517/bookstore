package com.bookstore.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Bookstore application")
                .description("Documentation generated Using SWAGGER2 for our User Rest API")
                .license("Test")
                .licenseUrl("https://test.com/nl/")
                .version("1.0.0")
                .contact(new Contact("test", "https://test.com/nl/", "test"))
                .build();
    }

    @Bean
    public Docket customImplementation() {

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bookstore"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
}
