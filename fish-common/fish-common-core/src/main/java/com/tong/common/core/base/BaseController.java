package com.tong.common.core.base;


import com.tong.common.core.constant.CommonConstant;
import com.tong.common.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @Author TR
 * @Create 2021/8/11 8:45
 * @Title: BaseController
 * @Description: 公共控制器
 */
@Slf4j
public class BaseController extends BaseResult<String> {
    private static final long serialVersionUID = -6441082442950752690L;

    /**
     * 得到request对象
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 得到response对象
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    protected String getToken() {
        return getRequest().getHeader(CommonConstant.PLAT_LOGINED_KEY);
    }

    /**
     * @Author TR
     * @Create 2021/8/16 9:40
     * @Title: getOperatorType
     * @Description: 获取当前平台登录用户类型: 会从请求头、cookie、请求参数中依次寻找操作员类型返回, 没有找到或转换失败，则返回null
     */
    protected Integer getOperatorType() {
        try {
            String id = getRequest().getHeader(CommonConstant.OPERATOR_TYPE_KEY);
            if (StringUtil.isEmpty(id)) {
                Optional<Cookie> cookieOptional = Arrays.stream(getRequest().getCookies()).filter(c ->
                        c.getName().equals(CommonConstant.OPERATOR_TYPE_KEY)
                ).findFirst();
                if (cookieOptional.isPresent()) {
                    id = getRequest().getParameter(CommonConstant.OPERATOR_TYPE_KEY);
                    if (StringUtil.isEmpty(id)) {
                        return null;
                    } else {
                        return Integer.parseInt(id);
                    }
                } else {
                    return Integer.parseInt(cookieOptional.get().getValue());
                }
            } else {
                return Integer.parseInt(id);
            }
        } catch (Exception e) {
            log.error("获取请求头中的operatorType参数失败！",e);
            return null;
        }
    }
}
