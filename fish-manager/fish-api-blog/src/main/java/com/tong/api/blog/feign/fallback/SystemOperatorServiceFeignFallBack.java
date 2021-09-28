package com.tong.api.blog.feign.fallback;


import com.tong.api.blog.feign.SystemOperatorServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.LoginUserInfo;
import com.tong.entity.blog.SystemOperator;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/11 14:51
 * @Title: SystemOperatorServiceFeignFallBack
 * @Description:
 */
@Component
public class SystemOperatorServiceFeignFallBack extends BaseResult<Object> implements FallbackFactory<SystemOperatorServiceFeign> {
    private static final long serialVersionUID = -1948844497284612938L;

    @Override
    public SystemOperatorServiceFeign create(Throwable e) {
        return new SystemOperatorServiceFeign() {
            @Override
            public BaseResult<HashMap<String, Object>> login(String operatorAccount, String operatorPwd, Integer operatorType) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：login 调用失败");
            }

            @Override
            public BaseResult<Object> loginOut(String token) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：loginOut 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> modifyPassword(Integer operatorId, String oldOperatorPwd, String newOperatorPwd) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：modifyPassword 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> register(String operatorAccount, String operatorPwd) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：register 调用失败");
            }

            @Override
            public BaseResult<SystemOperator> getCacheOperator(String token) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getCacheOperator 调用失败");
            }

            @Override
            public BaseResult<LoginUserInfo> getUserInfo(Integer operatorId) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getUserInfo 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> modifyUserInfo(SystemOperator operator) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：modifyUserInfo 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> addPopularity() {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：addPopularity 调用失败");
            }

        };
    }
}
