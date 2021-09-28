package com.tong.service.blog.dao.proxy;

import com.tong.entity.blog.LoginUserInfo;
import com.tong.entity.blog.SystemOperator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author TR
 * @Create 2021/8/11 9:12
 * @Title: SystemOperatorProxy
 * @Description:  操作员业务数据代理层
 */
@Mapper
public interface SystemOperatorProxy {

    /**
     * @Author TR
     * @Create 2021/8/11 9:31
     * @Title: SystemOperatorProxy.java
     * @Description: 获取操作员信息
     */
    SystemOperator getOperatorByAccount(@Param("operatorAccount") String operatorAccount,
                                        @Param("operatorType") Integer operatorType,
                                        @Param("seniorOperatorId") Integer seniorOperatorId);

    /**
     * @Author TR
     * @Create 2021/8/11 9:20
     * @Title: SystemOperatorProxy
     * @Description: 保存登录日志
     * @param: isOut
     * @param: isSuccess
     * */
    Integer saveLoginLog(@Param("operator") SystemOperator operator,
                         @Param("isOut") Integer isOut,
                         @Param("isSuccess") Integer isSuccess,
                         @Param("remark") String remark);

    /**
     * @Author TR
     * @Create 2021/8/12 9:01
     * @Title: modifyPassword
     * @Description: 修改密码
     */
    Integer updateOperatorById(@Param("operator") SystemOperator operator);

    /**
     * @Author TR
     * @Create 2021/8/12 9:11
     * @Title: saveSystemOperator
     * @Description:
     */
    Integer saveSystemOperator(@Param("operator") SystemOperator operator);

    /**
     * @Author TR
     * @Create 2021/8/23 14:28
     * @Title: getOperatorById
     * @Description: 根据ID获取用户的基本信息
     */
    LoginUserInfo getOperatorById(@Param("operatorId") Integer operatorId);

    /**
     * @Author TR
     * @Create 2021/8/24 17:00
     * @Title: updateBlogPopularity
     * @Description: 增加人气值
     */
    Integer updateBlogPopularity(@Param("blogPopularityId") Integer blogPopularityId);
}
