package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/12 15:07
 * @Title: CategoryInfo
 * @Description: 目录实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CategoryInfo extends BaseEntity {

    private static final long serialVersionUID = -8648029660643396289L;

    private Integer categoryInfoId;
    private String categoryInfoName;

    private Integer delFlag;
    private Integer createOperatorId;
    private String createTime;
}
