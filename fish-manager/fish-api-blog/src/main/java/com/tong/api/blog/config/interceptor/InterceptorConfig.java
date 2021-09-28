package com.tong.api.blog.config.interceptor;

import com.tong.common.core.config.UploadConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author TR
 * @Create 2021/8/12 10:00
 * @Title: InterceptorConfig
 * @Description: 登录验证拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(getTokenCheckInterceptor())
//				.addPathPatterns("/**")
//				.excludePathPatterns("/operator/login")// 登录
//				.excludePathPatterns("/operator/loginout")// 退出
//		;
	}

	@Bean
	public TokenCheckInterceptor getTokenCheckInterceptor() {
		return new TokenCheckInterceptor();
	}

	@Bean
	public UploadConfig uploadConfig() {
		return new UploadConfig();
	}
}