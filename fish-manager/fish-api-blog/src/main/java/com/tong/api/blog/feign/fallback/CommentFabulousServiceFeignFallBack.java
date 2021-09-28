package com.tong.api.blog.feign.fallback;

import com.tong.api.blog.feign.CommentFabulousServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Author TR
 * @Create 2021/8/22 0:11
 * @Title: CommentFabulousServiceFeignFallBack.java
 * @Description:
 */
@Component
public class CommentFabulousServiceFeignFallBack extends BaseResult<Object> implements FallbackFactory<CommentFabulousServiceFeign> {
    @Override
    public CommentFabulousServiceFeign create(Throwable e) {
        return new CommentFabulousServiceFeign() {

            @Override
            public BaseResult<HashMap<String, Object>> saveCommentInfo(CommentInfo commentInfo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：saveCommentInfo 调用失败");
            }

            @Override
            public BaseResult<PageVO<CommentCommunity>> getCommentCommunityInfo(CommentCommunityVo commentCommunityVo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getCommentCommunityInfo 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> savePersonalInterest(PersonalInterest personalInterest) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：savePersonalInterest 调用失败");
            }

            @Override
            public BaseResult<PageVO<PersonalCollection>> getPersonalCollection(Integer operatorId, Integer pageNum, Integer pageSize) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getPersonalCollection 调用失败");
            }

            @Override
            public BaseResult<PageVO<PersonalComment>> getPersonalComment(Integer operatorType, Integer operatorId, Integer pageNum, Integer pageSize, Integer position) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getPersonalComment 调用失败");
            }
        };
    }
}
