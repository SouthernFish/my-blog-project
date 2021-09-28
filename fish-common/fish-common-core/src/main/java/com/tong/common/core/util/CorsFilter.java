package com.tong.common.core.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 允许跨域响应过滤器
 * Created by WangShuai on 2016/7/30.
 */
@Configuration
@EnableWebMvc
public class CorsFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		// 指定允许其他域名访问
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, DELETE");
		// 响应头设置
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, HaiYi-Access-Token");
		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatus(204);
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
	}
}