package com.tong.api.blog.controller;

import com.tong.api.blog.config.annotation.CheckToken;
import com.tong.api.blog.feign.BlogLogRelevantServiceFeign;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 15:45
 * @Title: BlogLogRelevantController
 * @Description: blog日志相关业务
 */
@Api(tags = "blog日志相关业务")
@RestController
@RequestMapping("blog/log")
public class BlogLogRelevantController extends BaseController {
    private static final long serialVersionUID = -4511240131717250952L;

    @Autowired
    private BlogLogRelevantServiceFeign blogLogRelevantService;

    @Autowired
    private SystemOperatorServiceFeign systemOperatorService;

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: getCategoryList
     * @Description: 获取目录列表
     * @Param: position: 1 panel页
     */
    @GetMapping("/category/list")
    public BaseResult<List<CategoryInfo>> getCategoryList(Integer position, String searchText) {
        Integer operatorId = null;
        if (position != null && position.equals(1)) {
            // 获取当前登录人信息
            SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
            operatorId = systemOperator.getOperatorId();
        }
        return blogLogRelevantService.getCategoryList(operatorId, searchText);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: saveCategoryInfo
     * @Description: 保存分类信息
     */
    @PostMapping("/category/save")
    @CheckToken
    public BaseResult<HashMap<String, Object>> saveCategoryInfo(CategoryInfo categoryInfo) {
        // 获取当前登录人信息
        BaseResult<SystemOperator> systemOperatorBaseResult = systemOperatorService.getCacheOperator(getToken());
        SystemOperator systemOperator = systemOperatorBaseResult.getData();
        categoryInfo.setCreateOperatorId(systemOperator.getOperatorId());
        return blogLogRelevantService.saveCategoryInfo(categoryInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: getCategoryList
     * @Description: 获取标签列表
     * @Param: position: 1 panel页
     */
    @GetMapping("/tag/list")
    public BaseResult<List<TagInfo>> getTagList(Integer position, Integer categoryInfoId, String searchText) {
        Integer operatorId = null;
        if (position != null && position.equals(1)) {
            // 获取当前登录人信息
            SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
            operatorId = systemOperator.getOperatorId();
        }
        return blogLogRelevantService.getTagList(operatorId, categoryInfoId, searchText);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: saveCategoryInfo
     * @Description: 保存标签信息
     */
    @PostMapping("/tag/save")
    @CheckToken
    public BaseResult<HashMap<String, Object>> saveTagInfo(TagInfo tagInfo) {
        // 获取当前登录人信息
        BaseResult<SystemOperator> systemOperatorBaseResult = systemOperatorService.getCacheOperator(getToken());
        SystemOperator systemOperator = systemOperatorBaseResult.getData();
        tagInfo.setCreateOperatorId(systemOperator.getOperatorId());
        return blogLogRelevantService.saveTagInfo(tagInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/13 8:47
     * @Title: getArticleList
     * @Description: 获取文章列表
     * @Param: position: 1 panel页
     */
    @GetMapping("/article/list")
    public BaseResult<PageVO<ArticleInfo>> getArticleList(ArticleListVo articleListVo) {
        Integer operatorId = null;
        if (articleListVo.getPosition() != null && articleListVo.getPosition().equals(1)) {
            // 获取当前登录人信息
            SystemOperator systemOperator= systemOperatorService.getCacheOperator(getToken()).getData();
            articleListVo.setOperatorId(systemOperator.getOperatorId());
        }
        return blogLogRelevantService.getArticleList(articleListVo);
    }

    /**
     * @Author TR
     * @Create 2021/8/13 8:48
     * @Title: saveArticleInfo
     * @Description: 保存文章信息
     */
    @PostMapping("/article/save")
    @CheckToken
    public BaseResult<HashMap<String, Object>> saveArticleInfo(ArticleInfo articleInfo) {
        // 获取当前登录人信息
        BaseResult<SystemOperator> systemOperatorBaseResult = systemOperatorService.getCacheOperator(getToken());
        SystemOperator systemOperator = systemOperatorBaseResult.getData();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //设置日期格式
        String currentTime = df.format(new Date());  // new Date()为获取当前系统时间
        if(articleInfo.getOperationType().equals(1)) { // 新增
            articleInfo.setCreateOperatorId(systemOperator.getOperatorId());
            articleInfo.setCreateTime(currentTime);
            articleInfo.setLastUpdateTime(currentTime);
        } else if (articleInfo.getOperationType().equals(2)) { // 编辑
            articleInfo.setLastUpdateTime(currentTime);
        } else {
            articleInfo.setDelFlag(0);
            articleInfo.setDelTime(currentTime);
        }
        return blogLogRelevantService.saveArticleInfo(articleInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/14 20:08
     * @Title: getArticleInfo
     * @Description: 获取文章详情
     */
    @GetMapping("/article/info")
    public BaseResult<ArticleInfo> getArticleInfo(Integer articleInfoId) {
        Integer operatorId = null;
        if (getToken() == null || StringUtil.isEmpty(getToken())) {
            operatorId = 0;
        } else  {
            SystemOperator systemOperator = systemOperatorService.getCacheOperator(getToken()).getData();
            operatorId = systemOperator.getOperatorId();
        }
        return blogLogRelevantService.getArticleInfo(articleInfoId, operatorId);
    }

    /**
     * @Author TR
     * @Create 2021/8/18 15:24
     * @Title: getArticleStatistics
     * @Description: 获取统计数据
     */
    @GetMapping("/article/statistics")
    public BaseResult<ArticleStatistics> getArticleStatistics() {
        return blogLogRelevantService.getArticleStatistics();
    }

    /**
     * @Author TR
     * @Create 2021/8/18 16:57
     * @Title: getArticleLine
     * @Description: 文章生产线
     */
    @GetMapping("/article/line")
    public BaseResult<List<ArticleLine>> getArticleLine() {
        return blogLogRelevantService.getArticleLine();
    }
}
