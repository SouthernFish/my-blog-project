package com.tong.api.blog.controller;


import com.tong.api.blog.feign.UploadFileServiceFeign;
import com.tong.common.core.annotation.ApiMapping;
import com.tong.common.core.base.BaseController;
import com.tong.common.core.base.BaseResult;
import com.tong.entity.blog.ArticleUploadObs;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author TR
 * @Create 2021/8/15 18:54
 * @Title: UploadFileController
 * @Description: 文件上传相关业务
 */
@Api(tags = "文件上传相关业务")
@RestController
@RequestMapping("blog/upload")
public class UploadFileController extends BaseController {
    private static final long serialVersionUID = -4663112770638317995L;

    @Autowired
    private UploadFileServiceFeign uploadFileService;

    /**
     * @Author TR
     * @Create 2021/8/29 14:36
     * @Title: upLoadFileByFtp
     * @Description: ftp形式上传@RequestPart(value = "file") produces = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE},
     */

//    @PostMapping("/file/byftp")
    @ApiMapping(path = "/file/byftp", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResult<String> upLoadFileByFtp(@RequestPart(value="file") MultipartFile file) {
        System.out.println("===========upLoadFileByFtp====MultipartFile file==================================");
        System.out.println(file.toString());
        return uploadFileService.upLoadFileByFtp(file);
    }


    /**
     * @Author TR
     * @Create 2021/8/15 19:00
     * @Title: upLoadImage
     * @Description: 1 图片上传
     */
    @PostMapping("/image")
    public BaseResult<String> upLoadImage(MultipartFile file) {
        return uploadFileService.upLoadImageOrVideo(file, 1);
    }

    /**
     * @Author TR
     * @Create 2021/8/15 19:00
     * @Title: upLoadVideo
     * @Description: 2 视频上传
     */
    @PostMapping("/video")
    public BaseResult<String> upLoadVideo(MultipartFile file) {
        return uploadFileService.upLoadImageOrVideo(file, 2);
    }

    /**
     * @Author TR
     * @Create 2021/8/15 19:07
     * @Title: uploadTextFile
     * @Description: md文本内容上传到obs中存储为一个md文件
     */
    @PostMapping("/text")
    public BaseResult<String> uploadTextFile(ArticleUploadObs articleUploadObs){
        return uploadFileService.uploadTextFile(articleUploadObs);
    }

}

