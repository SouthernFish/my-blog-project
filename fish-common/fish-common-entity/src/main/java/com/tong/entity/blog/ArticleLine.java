package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/18 16:51
 * @Title: ArticleLine
 * @Description: 文章统计数据
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticleLine extends BaseEntity {
    private static final long serialVersionUID = 1159196957757674836L;

    // 文章_基础信息表ID
    private Integer articleInfoId;
    // 文字标题
    private String articleTitle;
    private String description;
    // 创建时间
    private String createTime;
}
