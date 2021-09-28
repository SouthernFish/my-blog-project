package com.tong.api.blog.config.interceptor;

import com.google.gson.Gson;
import com.tong.common.core.base.BaseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author TR
 * @Create 2021年03月06日
 * @Title: BaseInterceptor
 * @Description: 基础拦截器类
 */
public class BaseInterceptor extends BaseResult<Object> {

	private static final long serialVersionUID = 9034893933888618008L;

	/**
	 * @Author: TR
	 * @Create: 2021/0306
	 * @Title: writeResult
	 * @Description: 返回结果内容
	 */
	protected void writeResult(HttpServletResponse response, BaseResult<Object> baseResult) throws IOException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.getWriter().println(new Gson().toJson(baseResult));
		response.flushBuffer();
	}
}
