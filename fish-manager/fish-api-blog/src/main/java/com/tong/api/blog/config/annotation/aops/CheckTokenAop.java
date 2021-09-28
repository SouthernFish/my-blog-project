package com.tong.api.blog.config.annotation.aops;

import com.tong.api.blog.config.annotation.CheckToken;
import com.tong.api.blog.feign.SystemOperatorServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.SystemOperator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author TR
 * @Create 2021/8/12 9:52
 * @Title: CheckTokenAop
 * @Description: 验证登录切面
 */
@Component
@Aspect
public class CheckTokenAop extends BaseResult<Object> {

    private static final long serialVersionUID = 3482621269345664941L;

    @Autowired
    SystemOperatorServiceFeign systemOperatorService;

    /**
     * @param joinPoint
     * @return BaseResult
     * @throws Throwable
     * @author chentianjin
     * @date 2017年5月2日
     */
    @SuppressWarnings("unchecked")
    @Around("@annotation(com.tong.api.blog.config.annotation.CheckToken)")
    public BaseResult<Object> around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();

        // 获取方法注解
        CheckToken checkToken = ms.getMethod().getAnnotation(CheckToken.class);

        if (checkToken == null) {
            return null;
        }

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token)) {
            token = request.getParameter("token");
            if (StringUtil.isEmpty(token)) {
                return errorResult("您还未登录，请先登录");
            }
        }

        // 当token为null的时候说明未登录
        BaseResult<SystemOperator> result = systemOperatorService.getCacheOperator(token);
        if (result == null || result.getCode() != 200) {
            return errorResult(result.getMsg());
        }
        SystemOperator operator = result.getData();
        if (null == operator) {
            return errorResult("您还未登录，请先登录");
        }

        // 如果登录，则返回方法的返回值
        return (BaseResult<Object>) joinPoint.proceed();
    }

}
