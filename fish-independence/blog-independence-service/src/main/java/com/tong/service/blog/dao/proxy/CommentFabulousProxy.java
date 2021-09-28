package com.tong.service.blog.dao.proxy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tong.entity.blog.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author TR
 * @Create 2021/8/21 19:19
 * @Title: CommentFabulousProxy.java
 * @Description:
 */
@Mapper
public interface CommentFabulousProxy {

    Integer insertCommentInfo(@Param("commentInfo") CommentInfo commentInfo);

    Integer updateCommentInfo(@Param("commentInfo") CommentInfo commentInfo);

    IPage<CommentCommunity> getCommentCommunityInfo(@Param("page") Page<CommentCommunity> page,
                                                    @Param("articleInfoId") Integer articleInfoId,
                                                    @Param("parentId") Integer parentId,
                                                    @Param("operatorId") Integer operatorId,
                                                    @Param("orderType") String orderType);

    Integer insertPersonalInterest(@Param("personalInterest") PersonalInterest personalInterest);

    Integer updatePersonalInterest(@Param("interest") PersonalInterest personalInterest);


    IPage<PersonalCollection> getPersonalCollection(@Param("page") Page<PersonalCollection> page,
                                                    @Param("operatorId") Integer operatorId);

    IPage<PersonalComment> getPersonalComment(@Param("page") Page<PersonalComment> page,
                                              @Param("operatorType") Integer operatorType,
                                              @Param("operatorId") Integer operatorId,
                                              @Param("position") Integer position);
}
