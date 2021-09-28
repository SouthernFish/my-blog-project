package com.tong.service.blog.controller;

import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.ArticleUploadObs;
import com.tong.service.blog.service.UploadFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author TR
 * @Create 2021-08-10 17:33
 * @Title: SystemOperatorController
 * @Description: 操作员访问业务控制层
 */
@Api(tags = "操作员访问业务控制层")
@RestController
@ApiMapping("upload/file")
public class UploadFileController extends BaseController {
    private static final long serialVersionUID = -618369451453708535L;

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * @Author TR
     * @Create 2021/8/29 14:36
     * @Title: upLoadFileByFtp
     * @Description: ftp形式上传
     */
    @ApiMapping(path = "/byftp", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResult<String> upLoadFileByFtp(@RequestParam MultipartFile file) {
        return uploadFileService.upLoadFileByFtp(file);
    }

    /**
     * @Author TR
     * @Create 2021/8/15 18:48
     * @Title: upLoadImageOrVideo
     * @Description: uploadType 1 图片上传/ 2 视频上传
     */
    @PostMapping("/imgvideo")
    public BaseResult<String> upLoadImageOrVideo(MultipartFile file, Integer uploadType) {
        return uploadFileService.upLoadImageOrVideo(file, uploadType);
    }

    /**
     * @Author TR
     * @Create 2021/8/15 19:07
     * @Title: uploadTextFile
     * @Description: md文本内容上传到obs中存储为一个md文件
     */
    @PostMapping("/text")
    public BaseResult<String> uploadTextFile(@RequestBody ArticleUploadObs articleUploadObs){
        return uploadFileService.uploadTextFile(articleUploadObs);
    }

}
