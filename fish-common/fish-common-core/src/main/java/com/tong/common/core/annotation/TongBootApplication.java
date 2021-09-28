package com.tong.common.core.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author TR
 * @Create 2021-08-10 17:05
 * @Title: TongBootApplication
 * @Description: 自定义注解
 */
@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication
@ComponentScan(basePackages = "com.tong.*.")
@MapperScan(basePackages = "com.tong.*.*.dao.*")
@EnableDiscoveryClient
public @interface TongBootApplication {
    @AliasFor(annotation = EnableAutoConfiguration.class)
    Class<?>[] exclude() default {};
}
