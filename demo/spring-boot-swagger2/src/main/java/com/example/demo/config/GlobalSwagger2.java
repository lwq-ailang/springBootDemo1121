package com.example.demo.config;

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
 * Swagger2 配置类
 * <p>
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 通过@EnableSwagger2注解来启用Swagger2
 */
@Configuration
@EnableSwagger2
public class GlobalSwagger2 {

    @Bean//createApi函数创建Docket的Bean之后
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo()用来创建该Api的基本信息
                .apiInfo(apiInfo())
                //select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现
                //本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore指定的请求）。
                .select()
                //生成api文档扫描路径
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("SpringBootDemo Api")
                //作者
                .contact(new Contact("dukunbiao(null)", "https://github.com/dkbnull/SpringBootDemo", ""))
                //版本号
                .version("1.0")
                //描述
                .description("Base Java 1.8")
                .build();
    }

}
