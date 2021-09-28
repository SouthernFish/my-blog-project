package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * @Author TR
 * @Create 2021/8/21 22:21
 * @Title: CommentCommunityVo
 * @Description: 社区评论请求VO
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CommentCommunityVo extends BaseEntity {
    private static final long serialVersionUID = 826743726446793410L;

    private Integer selectType; // 1 评论 2 回复
    private Integer articleInfoId;
    private Integer parentId;
    private Integer operatorId;
    private Integer parentPageNum;
    private Integer parentPageSize;
    private Integer childPageNum;
    private Integer childPageSize;
}
