package com.tong.api.blog.feign;


import com.tong.api.blog.feign.fallback.SystemOperatorServiceFeignFallBack;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.LoginUserInfo;
import com.tong.entity.blog.SystemOperator;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/11 14:52
 * @Title: SystemOperatorServiceFeign
 * @Description:
 */
@Component
@RequestMapping("operator/access")
@FeignClient(value = "blogService", fallbackFactory = SystemOperatorServiceFeignFallBack.class)
public interface SystemOperatorServiceFeign {

    @PostMapping("/login")
    BaseResult<HashMap<String,Object>> login(@RequestParam("operatorAccount") String operatorAccount,
                                             @RequestParam("operatorPwd") String operatorPwd,
                                             @RequestParam("operatorType") Integer operatorType);

    @PostMapping("/loginout")
    BaseResult<Object> loginOut(@RequestParam("token") String token);

    @PostMapping("/modifypassword")
    BaseResult<HashMap<String,Object>> modifyPassword(@RequestParam("operatorId") Integer operatorId,
                                                      @RequestParam("oldOperatorPwd") String oldOperatorPwd,
                                                      @RequestParam("newOperatorPwd") String newOperatorPwd);

    @PostMapping("/register")
    BaseResult<HashMap<String,Object>> register(@RequestParam("operatorAccount") String operatorAccount,
                                                @RequestParam("operatorPwd") String operatorPwd);

    @GetMapping("/getcacheoperator")
    BaseResult<SystemOperator> getCacheOperator(@RequestParam("token") String token);

    @GetMapping("/userinfo")
    BaseResult<LoginUserInfo> getUserInfo(@RequestParam("operatorId") Integer operatorId);

    @PostMapping("/userinfo/modify")
    BaseResult<HashMap<String,Object>> modifyUserInfo(SystemOperator operator);

    @PostMapping("/add/popularity")
    BaseResult<HashMap<String,Object>> addPopularity();
}
