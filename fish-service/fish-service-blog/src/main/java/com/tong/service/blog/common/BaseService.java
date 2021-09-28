package com.tong.service.blog.common;

import com.google.gson.Gson;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.constant.CommonConstant;
import com.tong.common.core.util.MD5Utils;
import com.tong.common.core.util.RedisUtil;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.SystemOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.Random;

/**
 * 基础类
 * 
 * @author: chentianjin
 * @date: 2021年3月6日 下午4:16:20
 */
public class BaseService extends BaseResult<Object> {

	@Autowired
	protected RedisUtil redisUtil;

	@Autowired
	protected DataSourceTransactionManager transactionManager;

	private static final long serialVersionUID = 6481823382910905958L;

	/**
	 * 微信登录凭证校验
	 */
	public static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	protected String generateToken(Integer operatorId) {
		return MD5Utils.Md532(System.currentTimeMillis() + CommonConstant.YAN_PLAT_MANAGER + operatorId);
	}

	/**
	 * @Author TR
	 * @Create 2021/8/24 8:40
	 * @Title: getOperatorByToken
	 * @Description: 根据缓存获取当前用户
	 */
	protected SystemOperator getOperatorByToken(String token) {
		String operatorStr = redisUtil.get(token);
		if (StringUtils.isEmpty(operatorStr)) {
			return null;
		}
		SystemOperator operator = new Gson().fromJson(operatorStr, SystemOperator.class);
		return operator == null ? null : operator;
	}

	/**
	 * @Author TR
	 * @Create 2021/8/24 8:40
	 * @Title: validateCode
	 * @Description: 校验验证码
	 * @param: phoneNumber  电话号码
	 * @param: validateType 验证码类型
	 * @param: validateCode 验证码
	 * */
	protected boolean validateCode(String phoneNumber, String validateType, String validateCode) {
		try {
			String vcode = redisUtil.get(validateType + phoneNumber);
			if (StringUtil.isNotEmpty(vcode) && StringUtil.isNotEmpty(validateCode) && vcode.equals(validateCode)) {
//				redisUtil.setex(validateType + phoneNumber, 1, "");// 用过销毁
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @Author TR
	 * @Create 2021/8/24 8:41
	 * @Title: getRandom4
	 * @Description: 获取四位数的随机数
	 */
	private static String getRandom4() {
		Random random = new Random();
		String encode = "0123456789";
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = encode.charAt(random.nextInt(10)) + "";
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * @Author TR
	 * @Create 2021/8/24 8:41
	 * @Title: makeVcode
	 * @Description: 获得4位数的验证码
	 */
	public String makeVcode() {
		return getRandom4();
	}
}
