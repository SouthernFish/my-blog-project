package com.tong.common.core.constant;

/**
 * @Author TR
 * @Create 2021-08-10 17:37
 * @Title: StatusCode
 * @Description: 状态码常量
 */
public interface StatusCode {

	Long successCode = 200L;

	Long accessCode = 201L;

	/*
	 * 无登录信息
	 */
	Long noLogin = 402L;

	Long errorCode = 500L;
	
	/**
	 * 可以用户参数验证不通过时候返回
	 */
	Long errorParamCode = 400L;

}
