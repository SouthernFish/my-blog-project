package com.tong.service.blog.controller;

import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.LoginUserInfo;
import com.tong.service.blog.service.SystemOperatorService;
import com.tong.entity.blog.SystemOperator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021-08-10 17:33
 * @Title: SystemOperatorController
 * @Description: 操作员访问业务控制层
 */
@Api(tags = "操作员访问业务控制层")
@RestController
@ApiMapping("operator/access")
public class SystemOperatorController extends BaseController {

    private static final long serialVersionUID = -2132376161748306051L;

    @Autowired
    private SystemOperatorService systemOperatorService;


    /**
     * @Author TR
     * @Create 2021/8/11 15:59
     * @Title: login
     * @Description: 登录
     */
    @PostMapping("/login")
    public BaseResult<HashMap<String, Object>> login(String operatorAccount, String operatorPwd, Integer operatorType) {
        return systemOperatorService.login(operatorAccount, operatorPwd, operatorType);
    }


    /**
     * @Author TR
     * @Create 2021/8/11 10:33
     * @Title: loginOut
     * @Description: 退出登录
     */
    @PostMapping("/loginout")
    public BaseResult<Object> loginOut(String token) {
        return systemOperatorService.loginOut(token);
    }

    /**
     * @Author TR
     * @Create 2021/8/11 16:11
     * @Title: modifyPassword
     * @Description: 修改密码
     */
    @PostMapping("/modifypassword")
    public BaseResult<HashMap<String, Object>> modifyPassword(Integer operatorId, String oldOperatorPwd, String newOperatorPwd) {
        return systemOperatorService.modifyPassword(operatorId, oldOperatorPwd, newOperatorPwd);
    }

    /**
     * @Author TR
     * @Create 2021/8/11 15:58
     * @Title: register
     * @Description: 注册
     */
    @PostMapping("/register")
    public BaseResult<HashMap<String, Object>> register(String operatorAccount, String operatorPwd) {
        return systemOperatorService.register(operatorAccount, operatorPwd);
    }


    /**
     * @Author TR
     * @Create 2021/8/12 9:54
     * @Title: getCacheOperator
     * @Description: 根据token获得缓存用户
     */
    @GetMapping("/getcacheoperator")
    BaseResult<SystemOperator> getCacheOperator(String token) {
        return systemOperatorService.getCacheOperator(token);
    }

    /**
     * @Author TR
     * @Create 2021/8/16 9:59
     * @Title: getUserInfo
     * @Description: 根据ID获取用户的基本信息
     */
    @GetMapping("/userinfo")
    public BaseResult<LoginUserInfo> getUserInfo(Integer operatorId) {
        return systemOperatorService.getUserInfo(operatorId);
    }

    /**
     * @Author TR
     * @Create 2021/8/23 15:28
     * @Title: modifyUserInfo
     * @Description: 基本信息修改
     */
    @PostMapping("/userinfo/modify")
    public BaseResult<HashMap<String,Object>> modifyUserInfo(@RequestBody SystemOperator operator){

        return systemOperatorService.modifyUserInfo(operator);
    }

    /**
     * @Author TR
     * @Create 2021/8/24 15:14
     * @Title: addPopularity
     * @Description:  增加人气值
     */
    @PostMapping("/add/popularity")
    public BaseResult<HashMap<String,Object>> addPopularity() {
        return systemOperatorService.addPopularity();
    }
}
