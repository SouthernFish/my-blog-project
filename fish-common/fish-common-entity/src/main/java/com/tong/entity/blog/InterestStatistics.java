package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/21 20:38
 * @Title: InterestStatistics
 * @Description: 兴趣统计实体类
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class InterestStatistics extends BaseEntity {
    private static final long serialVersionUID = -262760512551676572L;

    // 人气值
    private Integer popularity;
    // 收藏数
    private Integer collection;
    // 回复数
    private Integer replyCount;
    // 是否喜欢
    private Integer isFabulous;
    // 是否收藏
    private Integer isCollection;
    // 个人喜好ID
    private Integer personalInterestId;



}
