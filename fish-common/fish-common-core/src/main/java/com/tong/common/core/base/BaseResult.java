package com.tong.common.core.base;

import com.tong.common.core.constant.StatusCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author TR
 * @Create 2021-08-10 17:39
 * @Title: BaseResult
 * @Description: 接口返回基础类
 */
@Data
@Accessors(chain = true)
public class BaseResult<T> implements Serializable {

	private static final long serialVersionUID = -5968576174989701204L;
	/**
	 * 业务错误码
	 */
	private long code;
	/**
	 * 结果集
	 */
	private T data;
	/**
	 * 描述
	 */
	private String msg;

	public BaseResult() {
	}

	private static <T> BaseResult<T> result(long code, String msg, T data) {
		BaseResult<T> result = new BaseResult<>();
		result.setCode(code);
		result.setData(data);
		result.setMsg(msg);
		return result;

	}

	public static <T> BaseResult<T> successResult(T data) {
		return successResult(StatusCode.successCode, "请求成功", data);
	}

	public static <T> BaseResult<T> successResult(String msg) {
		return successResult(StatusCode.successCode, msg, null);
	}

	public static <T> BaseResult<T> successResult(String msg, T data) {
		return successResult(StatusCode.successCode, msg, data);
	}

	public static <T> BaseResult<T> successResult(long code, String msg, T data) {
		return result(code, msg, data);
	}

	public static <T> BaseResult<T> errorResult(T data) {
		return errorResult(StatusCode.errorCode, "请求失败", data);
	}

	public static <T> BaseResult<T> errorResult(String msg) {
		return errorResult(StatusCode.errorCode, msg, null);
	}

	public static <T> BaseResult<T> errorResult(String msg, T data) {
		return errorResult(StatusCode.errorCode, msg, data);
	}
	public static <T> BaseResult<T> errorResult(long code, String msg) {
		return errorResult(code, msg, null);
	}

	public static <T> BaseResult<T> errorResult(long code, String msg, T data) {
		return result(code, msg, data);
	}

	/**
	 * 登录过期提示
	 */
	public static <T> BaseResult<T> noLoginResult() {
		return result(StatusCode.noLogin, "登录过期,请重新登录!", null);
	}

	public static <T> BaseResult<T> parameterErrorResult(String msg) {
		return errorResult(StatusCode.errorParamCode, msg);
	}
}
