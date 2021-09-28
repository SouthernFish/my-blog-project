package com.tong.api.blog.feign.fallback;

import com.tong.api.blog.feign.BlogLogRelevantServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.*;
import com.tong.entity.page.PageVO;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @Author TR
 * @Create 2021/8/12 15:43
 * @Title: BlogLogRelevantServiceFeignFallBack
 * @Description:
 */
@Component
public class BlogLogRelevantServiceFeignFallBack extends BaseResult<Object> implements FallbackFactory<BlogLogRelevantServiceFeign> {
    private static final long serialVersionUID = -6318378187227417120L;

    @Override
    public BlogLogRelevantServiceFeign create(Throwable e) {
        return new BlogLogRelevantServiceFeign() {

            @Override
            public BaseResult<List<CategoryInfo>> getCategoryList(Integer operatorId, String searchText) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getCategoryList 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> saveCategoryInfo(CategoryInfo categoryInfo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：saveCategoryInfo 调用失败");
            }

            @Override
            public BaseResult<List<TagInfo>> getTagList(Integer operatorId, Integer categoryInfoId, String searchText) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getTagList 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> saveTagInfo(TagInfo tagInfo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：saveTagInfo 调用失败");
            }

            @Override
            public BaseResult<PageVO<ArticleInfo>> getArticleList(ArticleListVo articleListVo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getArticleList 调用失败");
            }

            @Override
            public BaseResult<HashMap<String, Object>> saveArticleInfo(ArticleInfo articleInfo) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：saveArticleInfo 调用失败");
            }

            @Override
            public BaseResult<ArticleInfo> getArticleInfo(Integer articleInfoId, Integer operatorId) {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getArticleInfo 调用失败");
            }

            @Override
            public BaseResult<ArticleStatistics> getArticleStatistics() {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getArticleStatistics 调用失败");
            }

            @Override
            public BaseResult<List<ArticleLine>> getArticleLine() {
                e.printStackTrace();
                return errorResult("SystemOperatorServiceFeignFallBack：getArticleLine 调用失败");
            }
        };
    }
}
