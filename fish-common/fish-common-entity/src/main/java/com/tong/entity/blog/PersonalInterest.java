package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/21 20:49
 * @Title: PersonalInterest.java
 * @Description:  用户喜好实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class PersonalInterest extends BaseEntity {
    private static final long serialVersionUID = -7967851015290014574L;

    private Integer personalInterestId;
    private Integer objectType;
    private Integer articleInfoId;
    private Integer commentId;
    private Integer isFabulous;
    private Integer isCollection;
    private Integer createOperatorId;
    private String createTime;

}
