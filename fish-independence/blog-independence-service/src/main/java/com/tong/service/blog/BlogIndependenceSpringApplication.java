package com.tong.service.blog;

import com.tong.common.core.annotation.TongBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @Author TR
 * @Create 2021-08-10 16:54
 * @Title: BlogIndependenceSpringApplication
 * @Description: 服务启动类
 */
@TongBootApplication
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class BlogIndependenceSpringApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogIndependenceSpringApplication.class);
    }

    @PostConstruct
    void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogIndependenceSpringApplication.class, args);
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
