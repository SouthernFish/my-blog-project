package com.tong.api.blog.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author TR
 * @Create 2021/8/12 9:59
 * @Title: CheckParam
 * @Description: 验证登录信息用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface CheckParam {
	public String value() default "token";
}
