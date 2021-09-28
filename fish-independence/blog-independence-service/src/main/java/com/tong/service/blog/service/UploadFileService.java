package com.tong.service.blog.service;

import com.tong.common.core.base.BaseResult;
import com.tong.common.core.util.StringUtil;
import com.tong.entity.blog.ArticleUploadObs;
import com.tong.service.blog.common.BaseService;
import com.tong.service.blog.common.utils.FtpUploadUtil;
import com.tong.service.blog.common.utils.ObsUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author TR
 * @Create 2021/8/16 16:02
 * @Title: UploadFileService
 * @Description: 上传文件服务类
 */
@Service("uploadFileService")
public class UploadFileService extends BaseService {
    private static final long serialVersionUID = -6550729606904855417L;

    /**
     * @Author TR
     * @Create 2021/8/29 14:32
     * @Title: upLoadFileByFtp
     * @Description:  ftp形式上传
     */
    public BaseResult<String> upLoadFileByFtp(MultipartFile file) {
        String address = "";
        try {
            address = FtpUploadUtil.uploadFile(file);
            if (address != null && StringUtil.isNotEmpty(address)) {
                return successResult("上传成功！", address);
            } else {
                return errorResult("上传失败！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return errorResult("上传失败！");
        }
    }
}
