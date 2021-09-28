package com.tong.service.blog;

import com.tong.common.core.annotation.TongBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author TR
 * @Create 2021-08-10 16:54
 * @Title: BlogServiceSpringApplication
 * @Description: 服务启动类
 */
@TongBootApplication
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class BlogServiceSpringApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogServiceSpringApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogServiceSpringApplication.class, args);
    }
}
