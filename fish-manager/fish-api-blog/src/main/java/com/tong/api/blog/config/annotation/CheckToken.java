package com.tong.api.blog.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author TR
 * @Create 2021/8/12 9:58
 * @Title: CheckToken
 * @Description: 验证token注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CheckToken {
	public String value() default "token";
}
