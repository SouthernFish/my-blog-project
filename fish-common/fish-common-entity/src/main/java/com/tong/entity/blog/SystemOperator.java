package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/12 10:06
 * @Title: SystemOperator
 * @Description: 操作员信息实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class SystemOperator extends BaseEntity {

	private static final long serialVersionUID = 5565272327527860145L;

	private Integer operatorId;
	private String operatorAccount;
	private String operatorPwd;
	private Integer seniorOperatorId;
	private String operatorName;
	private String avatar;
	private String signature;
	private String operatorTel;
	private String operatorEmail;
	private String wx_unionid;
	private String qq_openid;
	private Integer operatorType;
	private Integer sex;
	private String birthday;
	private String createTime;
	private Integer status;
	private Integer delFlag;
}
