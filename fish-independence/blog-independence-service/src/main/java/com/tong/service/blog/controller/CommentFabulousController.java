package com.tong.service.blog.controller;

import com.tong.service.blog.service.CommentFabulousService;
import com.tong.service.blog.service.SystemOperatorService;
import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/21 19:17
 * @Title: CommentFabulousController
 * @Description: 留言点赞控制层
 */
@Api(tags = "留言点赞控制层")
@RestController
@RequestMapping("blog/comment")
public class CommentFabulousController extends BaseController {
    private static final long serialVersionUID = 3423965836131274249L;

    @Autowired
    private CommentFabulousService commentFabulousService;
    @Autowired
    private SystemOperatorService systemOperatorService;

    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: saveCommentInfo
     * @Description: 保存评论
     */
    @PostMapping("/save")
    public BaseResult<HashMap<String, Object>> saveCommentInfo(CommentInfo commentInfo) {
        // 获取当前登录人信息
        if (getToken() == null || StringUtil.isEmpty(getToken())) {
            return errorResult("您还没有登录，请先登录！");
        }
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
    public BaseResult<PageVO<PersonalComment>> getPersonalComment(Integer pageNum, Integer pageSize, Integer position) {
        SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
        return commentFabulousService.getPersonalComment(systemOperator.getOperatorType(), systemOperator.getOperatorId(), pageNum, pageSize, position);
    }



}
