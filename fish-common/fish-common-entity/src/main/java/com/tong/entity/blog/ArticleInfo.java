package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/12 16:53
 * @Title: ArticleInfo.java
 * @Description: 文章信息实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticleInfo extends InterestStatistics {
    private static final long serialVersionUID = -4192926308631274439L;

    // 文章_基础信息表ID
    private Integer articleInfoId;
    // 文字标题
    private String articleTitle;
    private String description;
    private String articleContent;
    // 文章存储的URL
    private String articleUrl;
    // 文章封面
    private String coverImgUrl;
    // 文章相册ID
    private Integer albumId;
    // 目录
    private Integer categoryInfoId;
    private String categoryInfoName;
    // 标签
    private Integer tagInfoId;
    private String tagInfoName;
    // 创建时间
    private String createTime;
    // 创建者ID
    private Integer createOperatorId;
    // 删除标识（1未删除、0删除）
    private Integer delFlag;
    // 删除时间
    private String delTime;
    // 最后更新时间
    private String lastUpdateTime;

    private Integer operationType; // 1 新增 2 编辑 3 删除
}
