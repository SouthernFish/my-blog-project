package com.tong.api.blog.feign;

import com.tong.api.blog.feign.fallback.CommentFabulousServiceFeignFallBack;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/22 0:10
 * @Title: CommentFabulousServiceFeign
 * @Description:
 */
@Component
@RequestMapping("fabulous")
@FeignClient(value = "blogService", fallbackFactory = CommentFabulousServiceFeignFallBack.class)
public interface CommentFabulousServiceFeign {

    @PostMapping("/comment/save")
    BaseResult<HashMap<String,Object>> saveCommentInfo(CommentInfo commentInfo);

    @GetMapping("/comment/community")
    BaseResult<PageVO<CommentCommunity>> getCommentCommunityInfo(CommentCommunityVo commentCommunityVo);

    @PostMapping("/interest/save")
    BaseResult<HashMap<String,Object>> savePersonalInterest(PersonalInterest personalInterest);

    @GetMapping("/personal/collection")
    BaseResult<PageVO<PersonalCollection>> getPersonalCollection(@RequestParam("operatorId") Integer operatorId,
                                                                 @RequestParam("pageNum") Integer pageNum,
                                                                 @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/personal/comment")
    BaseResult<PageVO<PersonalComment>> getPersonalComment(@RequestParam("operatorType") Integer operatorType,
                                                           @RequestParam("operatorId") Integer operatorId,
                                                           @RequestParam("pageNum") Integer pageNum,
                                                           @RequestParam("pageSize") Integer pageSize,
                                                           @RequestParam("position") Integer position);
}