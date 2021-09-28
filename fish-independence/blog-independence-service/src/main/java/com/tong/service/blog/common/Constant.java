package com.tong.service.blog.common;

import com.tong.common.core.util.ClassPathUtil;
import com.tong.common.core.util.ProFileReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 配置文件参数获取
 * 
 * @author: chentianjin
 * @date: 2021年3月6日 下午4:50:39
 */
public class Constant {

	/*
	 * 后台登录token失效时间默认一天
	 */
	public static long BLOG_TOKEN_TIME = 86400;

	/*
	 * 验证码有效时间
	 */
	public static long VALIDATE_CODE_TIME = 60;

	/*
	 * 短信接口路径
	 */
	public final static String API_URL_SMS = "https://yun.tim.qq.com/v3/tlssmssvr/sendsms?sdkappid=%s&random=%s";

	static {
		File file = new File(ClassPathUtil.getPath() + "resource/system.properties");
		ProFileReader uploadPropFile;
		try {
			uploadPropFile = new ProFileReader(new FileInputStream(file));
			BLOG_TOKEN_TIME = Integer.parseInt(uploadPropFile.getParamValue("BLOG_TOKEN_TIME"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
