package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/18 15:21
 * @Title: ArticleStatistics
 * @Description: 文章统计数据
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ArticleStatistics extends BaseEntity {
    private static final long serialVersionUID = 4147558817105982614L;

    private Integer articleTotal;
    private Integer categoryTotal;
    private Integer tagTotal;
}
