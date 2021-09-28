package com.tong.service.blog.controller;


import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import com.tong.service.blog.service.BlogLogRelevantService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 14:46
 * @Title: BlogLogRelevantController
 * @Description: 日志相关控制层
 */
@Api(tags = "日志相关控制层")
@RestController
@ApiMapping("log")
public class BlogLogRelevantController extends BaseController {

    private static final long serialVersionUID = 6831684551896202839L;

    @Autowired
    private BlogLogRelevantService blogLogRelevantService;

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: getCategoryList
     * @Description: 获取目录列表
     */
    @GetMapping("/category/list")
    public BaseResult<List<CategoryInfo>> getCategoryList(Integer operatorId, String searchText) {
        return blogLogRelevantService.getCategoryList(operatorId, searchText);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: saveCategoryInfo
     * @Description: 保存标签信息
     */
    @PostMapping("/category/save")
    public BaseResult<HashMap<String, Object>> saveCategoryInfo(@RequestBody CategoryInfo categoryInfo) {
        return blogLogRelevantService.saveCategoryInfo(categoryInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: getCategoryList
     * @Description: 获取目录列表
     */
    @GetMapping("/tag/list")
    public BaseResult<List<TagInfo>> getTagList(Integer operatorId, Integer categoryInfoId, String searchText) {
        return blogLogRelevantService.getTagList(operatorId, categoryInfoId, searchText);
    }

    /**
     * @Author TR
     * @Create 2021/8/12 15:02
     * @Title: saveCategoryInfo
     * @Description: 保存标签信息
     */
    @PostMapping("/tag/save")
    public BaseResult<HashMap<String, Object>> saveTagInfo(@RequestBody TagInfo tagInfo) {
        return blogLogRelevantService.saveTagInfo(tagInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/13 8:47
     * @Title: getArticleList
     * @Description: 获取文章列表
     */
    @ApiMapping(path = "/article/list", method = RequestMethod.POST)
    public BaseResult<PageVO<ArticleInfo>> getArticleList(@RequestBody ArticleListVo articleListVo) {
        return blogLogRelevantService.getArticleList(articleListVo);
    }

    /**
     * @Author TR
     * @Create 2021/8/13 8:48
     * @Title: saveArticleInfo
     * @Description: 保存文章信息
     */
    @PostMapping("/article/save")
    public BaseResult<HashMap<String, Object>> saveArticleInfo(@RequestBody ArticleInfo articleInfo) {
        return blogLogRelevantService.saveArticleInfo(articleInfo);
    }

    /**
     * @Author TR
     * @Create 2021/8/14 20:08
     * @Title: getArticleInfo
     * @Description: 获取文章详情
     */
    @GetMapping("/article/info")
    public BaseResult<ArticleInfo> getArticleInfo(Integer articleInfoId, Integer operatorId) {
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
