package com.select.integrated.config;

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
public class Swagger2Config {
    /**
     * 定义API分组
     */
    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                // 忽略类型
                // .ignoredParameterTypes(Pageable.class, LoginUser.class)
                .groupName("Integrated").select()
                .apis(RequestHandlerSelectors.basePackage("com.select.integrated.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Integrated")
                .description("接口访问地址：http://localhost:8090/, by WJQuery.")
                .termsOfServiceUrl("http://localhost:8090/")
                .version("1.0")
                .contact(new Contact("Integrated", "", "WJQuery@163.com"))
                .build();
    }
}
