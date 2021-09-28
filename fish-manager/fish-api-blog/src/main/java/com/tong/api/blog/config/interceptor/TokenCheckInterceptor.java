package com.tong.api.blog.config.interceptor;

import com.tong.api.blog.feign.SystemOperatorServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.constant.CommonConstant;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author TR
 * @Create 2021/8/12 10:02
 * @Title: TokenCheckInterceptor
 * @Description: 管理后台验证是否登录拦截器
 */
public class TokenCheckInterceptor extends BaseInterceptor implements HandlerInterceptor {

	private static final long serialVersionUID = -359478695260145702L;
	@Autowired
	SystemOperatorServiceFeign systemOperatorService;

	/**
	 * 请求处理之前进行调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("=======请求路径=========" + request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/");
		System.out.println("=======请求参数=========" + "token=" + request.getParameter("token") + ";params=" + request.getParameter("params"));
		String token = request.getHeader(CommonConstant.PLAT_LOGINED_KEY);
		if (StringUtil.isEmpty(token)) {
			token = request.getParameter(CommonConstant.PLAT_LOGINED_KEY);
			if (StringUtil.isEmpty(token)) {
				writeResult(response, noLoginResult());
				return false;
            }
		}

		// 获取登录用户信息： 包括权限之类的 systemOperatorService.getUserInfo(token);
		BaseResult<LoginUserInfo> result = null;
//		if (result.getCode() == StatusCode.successCode) {
//			LoginUserInfo loginUser = result.getData();
//			if (null == loginUser) {
//				writeResult(response, noLoginResult());
//				return false;
//			}
//			if(null == loginUser.getStatus() || null == loginUser.getDelFlag() || loginUser.getStatus().intValue() != 1 || loginUser.getDelFlag().intValue() != 1) {
//				writeResult(response, noLoginResult());
//			}
//		} else {
//			writeResult(response, noLoginResult());
//			return false;
//		}

		return true;
	}

	/**
	 * 整个请求结束之后,返回前台之后
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

	/**
	 * 整个请求结束之后，返回前台之前
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			writeResult(response, (BaseResult<Object>) modelAndView.getModel().get("baseResult"));
			modelAndView.clear();
		}
	}
}
