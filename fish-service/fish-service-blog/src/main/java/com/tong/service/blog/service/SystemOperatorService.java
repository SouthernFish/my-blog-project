package com.tong.service.blog.service;

import com.google.gson.Gson;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.constant.CommonConstant;
import com.tong.common.core.util.MD5Utils;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.LoginUserInfo;
import com.tong.service.blog.common.BaseService;
import com.tong.service.blog.common.Constant;
import com.tong.service.blog.dao.proxy.SystemOperatorProxy;
import com.tong.entity.blog.SystemOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/11 8:55
 * @Title: SystemOperatorService
 * @Description:  操作员访问业务服务层
 */
@Service("systemOperatorService")
public class SystemOperatorService extends BaseService {

    @Autowired
    private SystemOperatorProxy systemOperatorProxy;


    /**
     * @Author TR
     * @Create 2021/8/11 9:05
     * @Title: login
     * @Description:  操作员登录
     */
    public BaseResult<HashMap<String,Object>> login(String operatorAccount, String operatorPwd, Integer operatorType) {
        try {
            if (StringUtil.isEmpty(operatorAccount))
                return errorResult("请填写登录帐号!");
            if (StringUtil.isEmpty(operatorPwd))
                return errorResult("请填写登录密码!");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("验证参数出错!");
        }
        String remark = null;
        int isSuccess = 0;
        SystemOperator operator = null;

        try {
            HashMap<String, Object> result = new HashMap<String, Object>();

            operator = systemOperatorProxy.getOperatorByAccount(operatorAccount, operatorType);
            if (operator == null) {
                remark = "账号错误或不存在，请重新输入！";
                return errorResult("账号错误或不存在，请重新输入！");
            }
            String countErrorKey = "countError_" + operatorAccount;
            String countError = redisUtil.get(countErrorKey);

            Integer countErrorInt = 0;
            if (StringUtil.isNotEmpty(countError)) {
                countErrorInt = Integer.parseInt(countError);
                if (countErrorInt.intValue() > 4) {
                    remark = "密码错误_超出一小时尝试机会";
                    return errorResult("在一小时内密码错误4次，请一小时后再试!");
                }
            }
            if (!operator.getOperatorPwd().equals(MD5Utils.Md532(operatorPwd + CommonConstant.YAN_PLAT_MANAGER))) {
                remark = "密码错误_" + (countErrorInt.intValue() + 1);
                redisUtil.setex(countErrorKey, 3600, countErrorInt.intValue() + 1 + "");
                return errorResult("密码错误，请重新输入！");
            }

            if (operator.getStatus().intValue() == 0) {
                remark = "账号被禁用";
                return errorResult("账号被禁用，请联系管理员!");
            }

            String token = generateToken(operator.getOperatorId());
            redisUtil.setex(token, Constant.BLOG_TOKEN_TIME, new Gson().toJson(operator));// 默认一天
            redisUtil.setex(CommonConstant.BLOG_TOKEN_KEY +  operator.getOperatorId(), Constant.BLOG_TOKEN_TIME, token);

            result.put("token", token);
            result.put("operator", operator);
            isSuccess = 1;
            return successResult("登录成功!", result);
        } catch (Exception e) {
            e.printStackTrace();
            remark = "程序出错";
            isSuccess = 0;
            return errorResult("登录出错!");
        } finally {
            try {
                if (null != operator)
                    systemOperatorProxy.saveLoginLog(operator, 1, isSuccess, remark);
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
    }

    /**
     * @Author TR
     * @Create 2021/8/11 10:34
     * @Title: loginOut
     * @Description: 退出登录
     */
    public BaseResult<Object> loginOut(String token) {
        try {
            if (StringUtil.isEmpty(token)) {
                return errorResult("token参数必填");
            }
            SystemOperator operator = getOperatorByToken(token);
            redisUtil.delete(token);
            if (operator == null) {
                return successResult("退出成功!");
            }
            try {
                redisUtil.delete(CommonConstant.BLOG_TOKEN_KEY  + operator.getOperatorId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            systemOperatorProxy.saveLoginLog(operator, 0, 1, null);
            return successResult("退出成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("退出出错!");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 9:05
     * @Title: modifyPassword
     * @Description: 修改密码
     */
    public BaseResult<HashMap<String,Object>> modifyPassword(Integer operatorId, String oldOperatorPwd, String newOperatorPwd) {
        try {
            if (oldOperatorPwd.equals(newOperatorPwd)) {
                return errorResult("新密码与原密码相同");
            }
            newOperatorPwd = MD5Utils.Md532(newOperatorPwd + CommonConstant.YAN_PLAT_MANAGER);
            SystemOperator operator = new SystemOperator();
            operator.setOperatorId(operatorId);
            operator.setOperatorPwd(newOperatorPwd);

            System.out.println(operator.toString());

            systemOperatorProxy.updateOperatorById(operator);
            return successResult("密码修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("密码修改出错！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 9:34
     * @Title: register
     * @Description: 注册
     */
    public BaseResult<HashMap<String,Object>> register(String operatorAccount, String operatorPwd) {
        try {
            SystemOperator operator = systemOperatorProxy.getOperatorByAccount(operatorAccount, 2);
            if (operator.getOperatorAccount().equals(operatorAccount)) {
                return errorResult("账户已存在");
            }
            operatorPwd = MD5Utils.Md532(operatorPwd + CommonConstant.YAN_PLAT_MANAGER);
            operator.setOperatorAccount(operatorAccount);
            operator.setOperatorEmail(operatorAccount);
            operator.setOperatorPwd(operatorPwd);
            systemOperatorProxy.saveSystemOperator(operator);
            return successResult("注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("操作失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 9:56
     * @Title: getCacheOperator
     * @Description: 根据token获得缓存用户
     */
    public BaseResult<SystemOperator> getCacheOperator(String token) {
        try {
            if (StringUtil.isEmpty(token))
                return errorResult("token过期，请重新登录!");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("验证参数出错!");
        }

        try {
            return successResult("获取成功!", super.getOperatorByToken(token));
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取用户缓存出错!");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/16 10:06
     * @Title: getUserInfo
     * @Description: 根据ID获取用户的基本信息
     * @param operatorId
     */
    public  BaseResult<LoginUserInfo> getUserInfo(Integer operatorId) {
        if (operatorId == null) {
            return parameterErrorResult("用户ID为空");
        }
        LoginUserInfo loginUserInfo = systemOperatorProxy.getOperatorById(operatorId);
        return successResult("信息获取成功", loginUserInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/23 15:28
     * @Title: modifyUserInfo
     * @Description: 基本信息修改
     */
    public BaseResult<HashMap<String,Object>> modifyUserInfo(SystemOperator operator) {
        try {
            systemOperatorProxy.updateOperatorById(operator);
            return successResult("基本信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("基本信息修改出错！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/24 15:10
     * @Title: addPopularity
     * @Description: 增加人气值
     */
    public BaseResult<HashMap<String,Object>> addPopularity() {
        try {
            systemOperatorProxy.updateBlogPopularity();
            return successResult("操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("出错了！请联系管理员！");
        }
    }


    public static void main(String[] args) {
        String operatorPwd = "123456";
        System.out.println(MD5Utils.Md532(operatorPwd + CommonConstant.YAN_PLAT_MANAGER));
        System.out.println(MD5Utils.Md532("1234567" + CommonConstant.YAN_PLAT_MANAGER));
    }
}
