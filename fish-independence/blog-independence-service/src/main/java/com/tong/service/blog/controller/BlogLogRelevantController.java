package com.tong.service.blog.controller;




import com.tong.service.blog.service.BlogLogRelevantService;
import com.tong.service.blog.service.SystemOperatorService;
import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("blog/log")
public class BlogLogRelevantController extends BaseController {

    private static final long serialVersionUID = 6831684551896202839L;

    @Autowired
    private BlogLogRelevantService blogLogRelevantService;
    @Autowired
    private SystemOperatorService systemOperatorService;

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
     */
    @ApiMapping(path = "/article/list", method = RequestMethod.GET)
    public BaseResult<PageVO<ArticleInfo>> getArticleList(ArticleListVo articleListVo) {
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
    public BaseResult<ArticleStatistics> getArticleStatistics(Integer seniorOperatorId) {
        return blogLogRelevantService.getArticleStatistics(seniorOperatorId);
    }

    /**
     * @Author TR
     * @Create 2021/8/18 16:57
     * @Title: getArticleLine
     * @Description: 文章生产线
     */
    @GetMapping("/article/line")
    public BaseResult<List<ArticleLine>> getArticleLine(Integer seniorOperatorId) {
        return blogLogRelevantService.getArticleLine(seniorOperatorId);
    }

    /**
     * @Author TR
     * @Create 2021/9/16 10:55
     * @Title: saveDeleteImageInfo
     * @Description: 保存删除的图片信息
     */
    @PostMapping("/delete/image")
    public BaseResult saveDeleteImageInfo(String imagePath) {
        return blogLogRelevantService.saveDeleteImageInfo(imagePath);
    }
}
