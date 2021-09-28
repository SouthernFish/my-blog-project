package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/12 10:06
 * @Title: LoginUserInfo
 * @Description: 登录用户基本信息
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class LoginUserInfo extends BaseEntity {
    private static final long serialVersionUID = 509810525396567228L;

    private Integer operatorId;
    private String operatorName;
    private String avatar;
    private String signature;
    private String operatorTel;
    private String operatorEmail;
    private Integer popularity;
}
