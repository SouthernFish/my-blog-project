package com.tong.api.blog.feign;

import com.tong.api.blog.feign.fallback.BlogLogRelevantServiceFeignFallBack;
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
import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 15:44
 * @Title: BlogLogRelevantServiceFeign.java
 * @Description:
 */
@Component
@RequestMapping("log")
@FeignClient(value = "blogService", fallbackFactory = BlogLogRelevantServiceFeignFallBack.class)
public interface BlogLogRelevantServiceFeign {

    @GetMapping("/category/list")
    BaseResult<List<CategoryInfo>> getCategoryList(@RequestParam("operatorId") Integer operatorId,
                                                   @RequestParam("searchText") String searchText);

    @PostMapping("/category/save")
    BaseResult<HashMap<String,Object>> saveCategoryInfo(CategoryInfo categoryInfo);

    @GetMapping("/tag/list")
    BaseResult<List<TagInfo>> getTagList(@RequestParam("operatorId") Integer operatorId,
                                         @RequestParam("categoryInfoId") Integer categoryInfoId,
                                         @RequestParam("searchText") String searchText);

    @PostMapping("/tag/save")
    BaseResult<HashMap<String,Object>> saveTagInfo(TagInfo tagInfo);

    @GetMapping("/article/list")
    BaseResult<PageVO<ArticleInfo>> getArticleList(ArticleListVo articleListVo);

    @PostMapping("/article/save")
    BaseResult<HashMap<String,Object>> saveArticleInfo(ArticleInfo articleInfo);

    @GetMapping("/article/info")
    BaseResult<ArticleInfo> getArticleInfo(@RequestParam("articleInfoId") Integer articleInfoId,
                                           @RequestParam("operatorId") Integer operatorId);

    @GetMapping("/article/statistics")
    BaseResult<ArticleStatistics> getArticleStatistics();

    @GetMapping("/article/line")
    BaseResult<List<ArticleLine>> getArticleLine();
}
