package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/22 0:26
 * @Title: PersonalCollection.java
 * @Description:  我的收藏
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class PersonalCollection extends BaseEntity {
    private static final long serialVersionUID = 480642819281131064L;

    // 个人喜好ID
    private Integer personalInterestId;
    // 文章ID
    private Integer articleInfoId;
    // 文字标题
    private String articleTitle;
    // 目录
    private String categoryInfoName;
    // 标签
    private String tagInfoName;
    // 人气值
    private Integer popularity;
    // 收藏数
    private String collection;
}
