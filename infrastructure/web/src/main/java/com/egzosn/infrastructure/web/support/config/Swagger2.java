package com.egzosn.infrastructure.web.support.config;

import org.springframework.beans.factory.annotation.Value;
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
public class Swagger2 {

    //Swagger扫描的controller包目录
    @Value("${swagger.package}")
    private String basePackage;
    //标题
    @Value("${swagger.title}")
    private String title;
    //说明
    @Value("${swagger.description}")
    private String description;
    //网址
    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    //联系人姓名
    @Value("${swagger.contact.name}")
    private String contactName;
    //联系人个人网站
    @Value("${swagger.contact.url}")
    private String contactUrl;
    //联系人邮件
    @Value("${swagger.contact.email}")
    private String contactEmail;
    //版本号
    @Value("${swagger.version}")
    private String version;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }

}