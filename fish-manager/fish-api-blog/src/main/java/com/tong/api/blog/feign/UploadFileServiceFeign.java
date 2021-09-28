package com.tong.api.blog.feign;

import com.tong.api.blog.feign.fallback.UploadFileServiceFeignFallBack;
import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.ArticleUploadObs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author TR
 * @Create 2021/8/15 18:53
 * @Title: UploadFileServiceFeign.java
 * @Description:
 */
@Component
@RequestMapping("upload/file")
@FeignClient(value = "blogService", fallbackFactory = UploadFileServiceFeignFallBack.class)
public interface UploadFileServiceFeign {

    @ApiMapping(path = "/byftp", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BaseResult<String> upLoadFileByFtp(MultipartFile file);

    @PostMapping("/imgvideo")
    BaseResult<String> upLoadImageOrVideo(@RequestParam("file") MultipartFile file,
                                          @RequestParam("uploadType") Integer uploadType);

    @PostMapping("/text")
    BaseResult<String> uploadTextFile(ArticleUploadObs articleUploadObs);


}
