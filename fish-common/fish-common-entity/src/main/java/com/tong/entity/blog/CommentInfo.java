package com.tong.entity.blog;

import com.tong.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author TR
 * @Create 2021/8/21 20:42
 * @Title: CommentInfo
 * @Description: 留言信息实体
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CommentInfo extends InterestStatistics  {
    private static final long serialVersionUID = -7517889812585008713L;

    private Integer commentId;
    private String commentContent;
    private Integer articleInfoId;
    private Integer fromPerson;
    private Integer isReply;
    private Integer parentId;
    private Integer replyCommentId;
    private Integer toPerson;
    private Integer auditStatus;
    private Integer delFlag;
    private String createTime;

}
