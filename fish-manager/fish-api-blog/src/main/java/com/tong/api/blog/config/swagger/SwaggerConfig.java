package com.tong.api.blog.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author oran
 * @description swagger配置
 * @date 2021/3/21 17:12
 */
@Configuration
@EnableSwagger2
//@EnableSwaggerBootstrapUI
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean()
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.none())
                .paths(PathSelectors.none())
                .build();
    }
}
