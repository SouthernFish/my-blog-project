package com.tong.service.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import com.tong.service.blog.common.BaseService;
import com.tong.service.blog.dao.proxy.BlogLogRelevantProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author TR
 * @Create 2021/8/12 14:49
 * @Title: BlogLogRelevantService.java
 * @Description: 日志相关服务层
 */
@Service("blogLogRelevantService")
public class BlogLogRelevantService extends BaseService {

    private static final long serialVersionUID = -3160839447723428872L;

    @Autowired
    private BlogLogRelevantProxy blogLogRelevantProxy;


    /**
     * @Author TR
     * @Create 2021/8/12 15:15
     * @Title: getCategoryList
     * @Description: 获取目录列表
     */
    public BaseResult<List<CategoryInfo>> getCategoryList(Integer operatorId, String searchText) {
        try {
            // 返回结果列表
            List<CategoryInfo> categoryInfoList = blogLogRelevantProxy.getCategoryList(operatorId, searchText);
            return successResult("数据获取成功", categoryInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取目录列表失败！");
        }
    }


    /**
     * @Author TR
     * @Create 2021/8/12 15:28
     * @Title: saveCategoryInfo
     * @Description: 保存目录信息
     */
    public BaseResult<HashMap<String,Object>> saveCategoryInfo(CategoryInfo categoryInfo) {
        try {
            if (categoryInfo.getCategoryInfoId() == null) { // 保存
                if (categoryInfo.getCategoryInfoName() == null || StringUtil.isEmpty(categoryInfo.getCategoryInfoName())) {
                    return parameterErrorResult("目录名字不能为空！");
                }
                blogLogRelevantProxy.insertCategoryInfo(categoryInfo);
            } else { // 更新
                blogLogRelevantProxy.updateCategoryInfo(categoryInfo);
            }
            return successResult("目录信息保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("目录信息保存失败！");
        }
    }


    /**
     * @Author TR
     * @Create 2021/8/12 15:35
     * @Title: getTagList
     * @Description: 获取标签列表
     */
    public BaseResult<List<TagInfo>> getTagList(Integer operatorId, Integer categoryInfoId, String searchText) {
        try {
            // 返回结果列表
            List<TagInfo> categoryInfoList = blogLogRelevantProxy.getTagList(operatorId, categoryInfoId, searchText);
            return successResult("数据获取成功", categoryInfoList);
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取标签列表失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:37
     * @Title: saveTagInfo
     * @Description: 保存标签信息
     */
    public BaseResult<HashMap<String,Object>> saveTagInfo(TagInfo tagInfo) {
        try {
            if (tagInfo.getTagInfoId() == null) { // 保存
                if (tagInfo.getTagInfoName() == null || StringUtil.isEmpty(tagInfo.getTagInfoName())) {
                    return parameterErrorResult("标签名字不能为空！");
                }
                blogLogRelevantProxy.insertTagInfo(tagInfo);
            } else { // 更新
                blogLogRelevantProxy.updateTagInfo(tagInfo);
            }
            return successResult("标签信息保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("标签信息保存失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:35
     * @Title: getArticleList
     * @Description:  获取文章列表
     */
    public BaseResult<PageVO<ArticleInfo>> getArticleList(ArticleListVo articleListVo) {
        try {
            Page<ArticleInfo> page = new Page<>(articleListVo.getPageNum(), articleListVo.getPageSize());
            // 返回结果列表
            IPage<ArticleInfo> articleInfoList = blogLogRelevantProxy.getArticleList(page, articleListVo);
            return successResult("数据获取成功", new PageVO<>(articleInfoList));
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取文章列表失败！");
        }
    }


    /**
      * @Author TR
      * @Create 2021/8/13 8:45
      * @Title: saveArticleInfo
      * @Description: 保存文章信息
      */
    public BaseResult<HashMap<String,Object>> saveArticleInfo(ArticleInfo articleInfo) {
        try {
            if (articleInfo.getArticleInfoId() == null) { // 保存
                blogLogRelevantProxy.insertArticleInfo(articleInfo);
            } else { // 更新
                blogLogRelevantProxy.updateArticleInfo(articleInfo);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("articleInfoId", articleInfo.getArticleInfoId());
            return successResult("文章信息保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return errorResult("文章信息保存失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:35
     * @Title: getArticleInfo
     * @Description: 获取文章详情
     */
    public BaseResult<ArticleInfo> getArticleInfo(Integer articleInfoId, Integer operatorId) {
        try {
            // 返回结果
            ArticleInfo articleInfo = blogLogRelevantProxy.getArticleInfo(articleInfoId, operatorId);
            return successResult("数据获取成功", articleInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取文章详情失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/18 15:25
     * @Title: getArticleStatistics
     * @Description: 获取统计数据
     */
    public BaseResult<ArticleStatistics> getArticleStatistics() {
        try {
            // 返回结果
            ArticleStatistics articleStatistics = blogLogRelevantProxy.getArticleStatistics();
            return successResult("数据获取成功", articleStatistics);
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取统计数据失败！");
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/18 16:59
     * @Title: getArticleLine
     * @Description: 文章生产线
     */
    public BaseResult<List<ArticleLine>> getArticleLine() {
        try {
            // 返回结果
            List<ArticleLine> articleLine = blogLogRelevantProxy.getArticleLine();
            return successResult("数据获取成功", articleLine);
        }catch (Exception e) {
            e.printStackTrace();
            return errorResult("获取统计数据失败！");
        }
    }
}
