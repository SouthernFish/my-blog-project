package com.tong.service.blog.controller;

import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.LoginUserInfo;
import com.tong.service.blog.service.SystemOperatorService;
import com.tong.entity.blog.SystemOperator;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author TR
 * @Create 2021-08-10 17:33
 * @Title: SystemOperatorController
 * @Description: 操作员访问业务控制层
 */
@Api(tags = "操作员访问业务控制层")
@RestController
@RequestMapping("blog/operator")
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
    public BaseResult<HashMap<String, Object>> login(String operatorAccount, String operatorPwd, Integer operatorType, Integer seniorOperatorId) {
        return systemOperatorService.login(operatorAccount, operatorPwd, operatorType, seniorOperatorId);
    }


    /**
     * @Author TR
     * @Create 2021/8/11 10:33
     * @Title: loginOut
     * @Description: 退出登录
     */
    @PostMapping("/loginout")
    public BaseResult<Object> loginOut() {
        return systemOperatorService.loginOut(getToken());
    }

    /**
     * @Author TR
     * @Create 2021/8/11 16:11
     * @Title: modifyPassword
     * @Description: 修改密码
     */
    @PostMapping("/modify/password")
    public BaseResult<HashMap<String, Object>> modifyPassword(String oldOperatorPwd, String newOperatorPwd) {
        // 获取当前登录人信息
        SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
        return systemOperatorService.modifyPassword(systemOperator.getOperatorId(), oldOperatorPwd, newOperatorPwd);
    }

    /**
     * @Author TR
     * @Create 2021/8/11 15:58
     * @Title: register
     * @Description: 注册
     */
    @PostMapping("/register")
    public BaseResult<HashMap<String, Object>> register(String operatorAccount, String operatorPwd, Integer seniorOperatorId) {
        return systemOperatorService.register(operatorAccount, operatorPwd, seniorOperatorId);
    }


    /**
     * @Author TR
     * @Create 2021/8/12 9:54
     * @Title: getCacheOperator
     * @Description: 根据token获得缓存用户
     */
    @GetMapping("/cache/operator")
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
    public BaseResult<HashMap<String,Object>> modifyUserInfo(SystemOperator operator){
        // 获取当前登录人信息
        SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
        operator.setOperatorId(systemOperator.getOperatorId());
        return systemOperatorService.modifyUserInfo(operator);
    }

    /**
     * @Author TR
     * @Create 2021/8/24 15:14
     * @Title: addPopularity
     * @Description:  增加人气值
     */
    @PostMapping("/add/popularity")
    public BaseResult<HashMap<String,Object>> addPopularity(Integer blogPopularityId) {
        return systemOperatorService.addPopularity(blogPopularityId);
    }

    /**
     * @Author TR
     * @Create 2021/8/16 10:55
     * @Title: getPublicKey
     * @Description: 后台登录请求加密key, 根据后台框架js代码改编
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/publicKey")
    public BaseResult getPublicKey() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("mockServer", true); // 设置为true，可以禁用加密
        map.put("publicKey",
                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBT2vr+dhZElF73FJ6xiP181txKWUSNLPQQlid6DUJhGAOZblluafIdLmnUyKE8mMHhT3R+Ib3ssZcJku6Hn72yHYj/qPkCGFv0eFo7G+GJfDIUeDyalBN0QsuiE/XzPHJBuJDfRArOiWvH0BXOv5kpeXSXM8yTt5Na1jAYSiQ/wIDAQAB");
        return successResult(map);
    }
}
