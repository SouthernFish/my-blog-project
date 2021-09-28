package com.tong.api.blog.feign.fallback;

import com.tong.api.blog.feign.UploadFileServiceFeign;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.ArticleUploadObs;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author TR
 * @Create 2021/8/15 18:52
 * @Title: UploadFileServiceFeignFallBack
 * @Description:
 */
@Component
public class UploadFileServiceFeignFallBack extends BaseResult<Object> implements FallbackFactory<UploadFileServiceFeign> {
    private static final long serialVersionUID = 6693325074946069850L;

    @Override
    public UploadFileServiceFeign create(Throwable e) {
        return new UploadFileServiceFeign() {
            @Override
            public BaseResult<String> upLoadFileByFtp(MultipartFile file) {
                e.printStackTrace();
                return errorResult("UploadFileServiceFeignFallBack：upLoadFileByFtp 调用失败");
            }

            @Override
            public BaseResult<String> upLoadImageOrVideo(MultipartFile file, Integer uploadType) {
                e.printStackTrace();
                return errorResult("UploadFileServiceFeignFallBack：upLoadImageOrVideo 调用失败");
            }

            @Override
            public BaseResult<String> uploadTextFile(ArticleUploadObs articleUploadObs) {
                e.printStackTrace();
                return errorResult("UploadFileServiceFeignFallBack：uploadTextFile 调用失败");
            }
        };
    }
}
