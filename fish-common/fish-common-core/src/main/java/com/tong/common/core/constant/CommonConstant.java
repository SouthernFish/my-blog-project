package com.tong.common.core.constant;


/**
 * @Author TR
 * @Create 2021-08-10 17:42
 * @Title: CommonConstant
 * @Description: 公共常量类
 */
public class CommonConstant {
    /**
     * blog登录用户登录标识的key
     **/
    public static final String PLAT_LOGINED_KEY = "token";
    /**
     * 后台密码加密用盐
     **/
    public static final String YAN_PLAT_MANAGER = "BLOG_MANAGER_$789787@//*-+'[]ctj;384785*^*&%^%$%";// 加盐


    /**
     * blog操作用户类型的key
     **/
    public static final String OPERATOR_TYPE_KEY = "_TONG_OPERATOR_TYPE_";
    /**
     * 后台默认密码
     **/
    public static final String DEFAULE_OPERATOR_PWD = "123456";

    /**
     * 小程序session_key缓存前缀
     **/
    public static final String MINI_SESSION_KEY = "MINI_SESSION_KEY_";

    /**
     * 后台用户token前缀key
     **/
    public static final String BLOG_TOKEN_KEY = "BLOG_TOKEN_KEY_";

    /**
     * 小程序session等信息缓存默认时间，单位：s
     **/
    public static final Integer MINI_SESSION_SECONDS = 7200;

    /**
     * 公共用户信息缓存时间 一小时
     */
    public static final Integer COMMON_WXMGR_USER_SECONDS = 3600;

}
