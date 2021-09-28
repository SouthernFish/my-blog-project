package com.tong.api.blog;

import com.tong.common.core.annotation.TongBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
/**
 * @Author TR
 * @Create 2021/9/7 15:10
 * @Title: BlogApiSpringApplication.java
 * @Description: 服务启动类
 */

@TongBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableHystrix
@EnableFeignClients(basePackages = "com.tong.api.blog.*")
@Configuration
public class BlogApiSpringApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogApiSpringApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApiSpringApplication.class, args);
    }

    /**
     * 设置跨域
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

}
