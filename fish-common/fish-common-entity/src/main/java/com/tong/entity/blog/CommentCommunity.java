package com.tong.entity.blog;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/21 22:04
 * @Title: CommentCommunity.java
 * @Description:
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class CommentCommunity extends CommentInfo {
    private static final long serialVersionUID = 4330245872503978552L;

    private List<CommentCommunity> child;
    private Integer replyCount;
    private String fromPersonName;
    private String toPersonName;
    private String avatar;

    private boolean isReplyLaunch = false;
}
