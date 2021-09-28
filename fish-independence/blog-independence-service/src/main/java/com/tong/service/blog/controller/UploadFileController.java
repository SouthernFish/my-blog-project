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
@RequestMapping("blog/upload")
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
    @ApiMapping(path = "/file/byftp", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResult<String> upLoadFileByFtp(@RequestPart(value="file")  MultipartFile file) {
        System.out.println("===========upLoadFileByFtp====MultipartFile file==================================");
        System.out.println(file.toString());
        return uploadFileService.upLoadFileByFtp(file);
    }
}
