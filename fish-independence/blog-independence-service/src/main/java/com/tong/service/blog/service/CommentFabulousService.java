package com.tong.service.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import com.tong.service.blog.common.BaseService;
import com.tong.service.blog.dao.proxy.CommentFabulousProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/21 19:18
 * @Title: CommentFabulousService.java
 * @Description: 留言点赞相关服务层
 */
@Service("commentFabulousService")
public class CommentFabulousService extends BaseService {
    private static final long serialVersionUID = -9061347310331981832L;

    @Autowired
    private CommentFabulousProxy commentFabulousProxy;

    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: saveCommentInfo
     * @Description: 保存评论
     */
    public BaseResult<HashMap<String,Object>> saveCommentInfo(CommentInfo commentInfo) {
        try {
            if (commentInfo.getCommentId() == null) { // 保存
                commentFabulousProxy.insertCommentInfo(commentInfo);
            } else { // 更新
                commentFabulousProxy.updateCommentInfo(commentInfo);
            }
            return successResult("评论成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("操作失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/21 21:40
     * @Title: getCommentCommunityInfo
     * @Description: 评论区信息
     */
    public BaseResult<PageVO<CommentCommunity>> getCommentCommunityInfo(CommentCommunityVo commentVo) {
        try {
            // selectType 1 评论 2 回复
            Page<CommentCommunity> page = null;
            IPage<CommentCommunity> commentInfo = null;
            String orderType = commentVo.getSelectType().equals(1) ? "DESC" : "ASC";
            if (commentVo.getSelectType().equals(1)) { // 父级和子级
                page = new Page<>(commentVo.getParentPageNum(), commentVo.getParentPageSize());
                // 返回结果列表
                commentInfo = commentFabulousProxy.getCommentCommunityInfo(page, commentVo.getArticleInfoId(), commentVo.getParentId(), commentVo.getOperatorId(), orderType);
                if (commentVo.getParentId().equals(0) && commentInfo.getRecords() != null && commentInfo.getRecords().size() > 0) {
                    page = new Page<>(commentVo.getChildPageNum(), commentVo.getChildPageSize());
                    for (CommentCommunity comment: commentInfo.getRecords()) {
                        if (comment.getReplyCount() > 0) { // 回复列表
                            IPage<CommentCommunity> child = commentFabulousProxy.getCommentCommunityInfo(page, commentVo.getArticleInfoId(), comment.getCommentId(), commentVo.getOperatorId(), orderType);
                            comment.setChild(child.getRecords());
                        }
                    }
                }
            }
            // 只查子级
            if (commentVo.getSelectType().equals(2)) {
                page = new Page<>(commentVo.getChildPageNum(), commentVo.getChildPageSize());
                commentInfo = commentFabulousProxy.getCommentCommunityInfo(page, commentVo.getArticleInfoId(), commentVo.getParentId(), commentVo.getOperatorId(), orderType);
            }
            return successResult("数据获取成功", new PageVO<CommentCommunity>(commentInfo));
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取评论数据失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/21 21:15
     * @Title: savePersonalInterest
     * @Description: 保存个人兴趣
     */
    public BaseResult<HashMap<String,Object>> savePersonalInterest(PersonalInterest personalInterest) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            if (personalInterest.getPersonalInterestId() == null) { // 保存
                commentFabulousProxy.insertPersonalInterest(personalInterest);
            } else { // 更新
                commentFabulousProxy.updatePersonalInterest(personalInterest);
            }
            result.put("personalInterestId", personalInterest.getPersonalInterestId());
            return successResult("评论成功！", result);
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("操作失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalCollection
     * @Description:  获取个人收藏
     */
    public BaseResult<PageVO<PersonalCollection>> getPersonalCollection(Integer operatorId, Integer pageNum, Integer pageSize) {
        try {
            Page<PersonalCollection> page = new Page<>(pageNum, pageSize);
            IPage<PersonalCollection> personalCollection = commentFabulousProxy.getPersonalCollection(page, operatorId);
            return successResult("数据获取成功", new PageVO<PersonalCollection>(personalCollection));
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取文章列表失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalComment
     * @Description:  获取个人留言
     */
    public BaseResult<PageVO<PersonalComment>> getPersonalComment(Integer operatorType, Integer operatorId, Integer pageNum, Integer pageSize, Integer position) {
        try {
            Page<PersonalComment> page = new Page<>(pageNum, pageSize);
            IPage<PersonalComment> personalComment = commentFabulousProxy.getPersonalComment(page, operatorType, operatorId, position);
            return successResult("数据获取成功", new PageVO<PersonalComment>(personalComment));
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取文章列表失败！");
        }
    }
}
