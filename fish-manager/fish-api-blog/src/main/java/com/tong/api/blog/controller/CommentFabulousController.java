package com.tong.api.blog.controller;

import com.tong.api.blog.config.annotation.CheckToken;
import com.tong.api.blog.feign.CommentFabulousServiceFeign;
import com.tong.api.blog.feign.SystemOperatorServiceFeign;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/22 0:08
 * @Title: CommentFabulousController
 * @Description: 留言点赞相关业务
 */
@Api(tags = "blog日志相关业务")
@RestController
@RequestMapping("fabulous/comment")
public class CommentFabulousController extends BaseController {
    private static final long serialVersionUID = -1375505845784947415L;
    
    @Autowired
    private CommentFabulousServiceFeign commentFabulousService;

    @Autowired
    private SystemOperatorServiceFeign systemOperatorService;

    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: saveCommentInfo
     * @Description: 保存评论
     */
    @PostMapping("/save")
    @CheckToken
    public BaseResult<HashMap<String, Object>> saveCommentInfo(CommentInfo commentInfo) {
        // 获取当前登录人信息
        SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
        commentInfo.setFromPerson(systemOperator.getOperatorId());
        return commentFabulousService.saveCommentInfo(commentInfo);
    }


    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: getCommentCommunityInfo
     * @Description: 评论区信息获取
     */
    @GetMapping("/community")
    public BaseResult<PageVO<CommentCommunity>> getCommentCommunityInfo(CommentCommunityVo commentCommunityVo) {
        Integer operatorId = null;
        if (getToken() == null || StringUtil.isEmpty(getToken())) {
            commentCommunityVo.setOperatorId(0);
        } else  {
            SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
            commentCommunityVo.setOperatorId(systemOperator.getOperatorId());
        }
        return commentFabulousService.getCommentCommunityInfo(commentCommunityVo);
    }

    /**
     * @Author TR
     * @Create 2021/8/21 21:15
     * @Title: savePersonalInterest
     * @Description: 保存个人兴趣
     */
    @PostMapping("/interest/save")
    public BaseResult<HashMap<String, Object>> savePersonalInterest(PersonalInterest personalInterest) {
        if (getToken() == null || StringUtil.isEmpty(getToken())) {
            personalInterest.setCreateOperatorId(-1);
        } else {
            SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
            personalInterest.setCreateOperatorId(systemOperator.getOperatorId());
        }
        return commentFabulousService.savePersonalInterest(personalInterest);
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalCollection
     * @Description:  获取个人收藏
     */
    @CheckToken
    @GetMapping("/personal/collection")
    public BaseResult<PageVO<PersonalCollection>> getPersonalCollection(Integer pageNum, Integer pageSize) {
        SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
        return commentFabulousService.getPersonalCollection(systemOperator.getOperatorId(), pageNum, pageSize);
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalComment
     * @Description:  获取个人留言
     */
    @GetMapping("/personal/comment")
    @CheckToken
    public BaseResult<PageVO<PersonalComment>> getPersonalComment(Integer pageNum, Integer pageSize, Integer position) {
        SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
        return commentFabulousService.getPersonalComment(systemOperator.getOperatorType(), systemOperator.getOperatorId(), pageNum, pageSize, position);
    }
    
}
