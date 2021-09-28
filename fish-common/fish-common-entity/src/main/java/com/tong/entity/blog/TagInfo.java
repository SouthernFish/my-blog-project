package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @Author TR
 * @Create 2021/8/12 15:08
 * @Title: TagInfo
 * @Description:  标签信息实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class TagInfo extends BaseEntity {

    private static final long serialVersionUID = 8617668726455087892L;

    private Integer tagInfoId;
    private String tagInfoName;
    private  String coverImgUrl;
    private Integer categoryInfoId;
    private Integer delFlag;
    private Integer createOperatorId;
    private String createTime;

}
