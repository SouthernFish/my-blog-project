package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/22 0:33
 * @Title: PersonalComment
 * @Description: 个人留言实体
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class PersonalComment extends BaseEntity {
    private static final long serialVersionUID = -4393738595208566779L;

    // 个人喜好ID
    private Integer commentId;
    private String operatorName;
    private String articleTitle;
    private String commentContent;
    private String createTime;
    private Integer popularity;  // 人气值
    private Integer replyCount;  // 回复数

}
