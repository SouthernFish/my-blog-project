package com.tong.service.blog.dao.proxy;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tong.entity.blog.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 14:51
 * @Title: BlogLogRelevantProxy
 * @Description: 日志相关数据代理层
 */
@Mapper
public interface BlogLogRelevantProxy {

    /**
     * @Author TR
     * @Create 2021/8/12 15:24
     * @Title: getCategoryList
     * @Description: 获取目录列表
     */
    List<CategoryInfo> getCategoryList(@Param("operatorId") Integer operatorId,
                                       @Param("searchText") String searchText);

    /**
     * @Author TR
     * @Create 2021/8/12 15:33
     * @Title: insertCategoryInfo
     * @Description: 插入目录信息
     */
    Integer insertCategoryInfo(@Param("categoryInfo") CategoryInfo categoryInfo);

    /**
     * @Author TR
     * @Create 2021/8/12 15:34
     * @Title: updateCategoryInfo
     * @Description: 更新目录信息
     */
    Integer updateCategoryInfo(@Param("categoryInfo") CategoryInfo categoryInfo);


    /**
     * @Author TR
     * @Create 2021/8/12 15:37
     * @Title: getTagList
     * @Description: 获取标签列表
     */
    List<TagInfo> getTagList(@Param("operatorId") Integer operatorId,
                             @Param("categoryInfoId") Integer categoryInfoId,
                             @Param("searchText") String searchText);

    /**
     * @Author TR
     * @Create 2021/8/12 15:38
     * @Title: insertTagInfo
     * @Description: 插入标签信息
     */
    Integer insertTagInfo(@Param("tagInfo") TagInfo tagInfo);


    /**
     * @Author TR
     * @Create 2021/8/12 15:38
     * @Title: updateTagInfo
     * @Description: 更新标签信息
     */
    Integer updateTagInfo(@Param("tagInfo") TagInfo tagInfo);


    /**
     * @Author TR
     * @Create 2021/8/12 15:37
     * @Title: getArticleList
     * @Description: 获取文章列表
     */
    IPage<ArticleInfo> getArticleList(Page<ArticleInfo> page,
                                      @Param("articleListVo") ArticleListVo articleListVo);

    /**
     * @Author TR
     * @Create 2021/8/12 15:38
     * @Title: insertArticleInfo
     * @Description: 插入文章信息
     */
    Integer insertArticleInfo(@Param("article") ArticleInfo articleInfo);


    /**
     * @Author TR
     * @Create 2021/8/12 15:38
     * @Title: updateArticleInfo
     * @Description: 更新文章信息
     */
    Integer updateArticleInfo(@Param("article") ArticleInfo articleInfo);

    /**
     * @Author TR
     * @Create 2021/8/14 20:03
     * @Title: getArticleInfo
     * @Description: 获取文章详情
     */
    ArticleInfo getArticleInfo(@Param("articleInfoId") Integer articleInfoId,
                               @Param("operatorId") Integer operatorId);

    /**
     * @Author TR
     * @Create 2021/8/18 15:26
     * @Title: getArticleStatistics
     * @Description: 获取统计数据
     * @param seniorOperatorId
     */
    ArticleStatistics getArticleStatistics(@Param("seniorOperatorId") Integer seniorOperatorId);

    /**
     * @Author TR
     * @Create 2021/8/18 16:59
     * @Title: getArticleLine
     * @Description: 文章生产线
     */
    List<ArticleLine> getArticleLine(@Param("seniorOperatorId") Integer seniorOperatorId);

    /**
     * @Author TR
     * @Create 2021/9/16 10:55
     * @Title: saveDeleteImageInfo
     * @Description: 保存删除的图片信息 saveDeleteImageInfo
     */
    Integer saveDeleteImageInfo(@Param("imageArr") String[] imageArr);
}
