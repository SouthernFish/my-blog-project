package com.tong.service.blog.controller;

import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import com.tong.service.blog.service.CommentFabulousService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/21 19:17
 * @Title: CommentFabulousController
 * @Description: 留言点赞控制层
 */
@Api(tags = "留言点赞控制层")
@RestController
@ApiMapping("fabulous")
public class CommentFabulousController extends BaseController {
    private static final long serialVersionUID = 3423965836131274249L;

    @Autowired
    private CommentFabulousService commentFabulousService;

    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: saveCommentInfo
     * @Description: 保存评论
     */
    @PostMapping("/comment/save")
    public BaseResult<HashMap<String, Object>> saveCommentInfo(@RequestBody CommentInfo commentInfo) {
        return commentFabulousService.saveCommentInfo(commentInfo);
    }


    /**
     * @Author TR
     * @Create 2021/8/21 21:04
     * @Title: getCommentCommunityInfo
     * @Description: 评论区信息获取
     */
    @PostMapping("/comment/community")
    public BaseResult<PageVO<CommentCommunity>> getCommentCommunityInfo(@RequestBody CommentCommunityVo commentCommunityVo) {
        return commentFabulousService.getCommentCommunityInfo(commentCommunityVo);
    }

    /**
     * @Author TR
     * @Create 2021/8/21 21:15
     * @Title: savePersonalInterest
     * @Description: 保存个人兴趣
     */
    @PostMapping("/interest/save")
    public BaseResult<HashMap<String, Object>> savePersonalInterest(@RequestBody PersonalInterest personalInterest) {
        return commentFabulousService.savePersonalInterest(personalInterest);
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalCollection
     * @Description:  获取个人收藏
     */
    @GetMapping("/personal/collection")
    public BaseResult<PageVO<PersonalCollection>> getPersonalCollection(Integer operatorId, Integer pageNum, Integer pageSize) {
        return commentFabulousService.getPersonalCollection(operatorId, pageNum, pageSize);
    }

    /**
     * @Author TR
     * @Create 2021/8/22 0:31
     * @Title: getPersonalComment
     * @Description:  获取个人留言
     */
    @GetMapping("/personal/comment")
    public BaseResult<PageVO<PersonalComment>> getPersonalComment(Integer operatorType, Integer operatorId, Integer pageNum, Integer pageSize, Integer position) {
        return commentFabulousService.getPersonalComment(operatorType, operatorId, pageNum, pageSize, position);
    }



}
