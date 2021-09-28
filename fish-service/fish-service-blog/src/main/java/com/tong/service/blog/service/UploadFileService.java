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

    /**
     * @Author TR
     * @Create 2021/8/15 18:44
     * @Title: upLoadImageOrVideo
     * @Description: uploadType 1 图片上传/ 2 视频上传
     */
    public BaseResult<String> upLoadImageOrVideo(MultipartFile file, Integer uploadType) {
        String address = "";
        try {
            if (uploadType.equals(1)) {
                address = ObsUploadUtil.uploadImage(file);
            }
            if (uploadType.equals(2)) {
                address = ObsUploadUtil.uploadVideo(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return errorResult("存储失败！");
        }
        return successResult("存储成功！", address);
    }

    /**
     * @Author TR
     * @Create 2021/8/15 19:07
     * @Title: uploadTextFile
     * @Description: md文本内容上传到obs中存储为一个md文件
     */
    public BaseResult<String> uploadTextFile(ArticleUploadObs articleUploadObs) {
        if (articleUploadObs == null || articleUploadObs.getFolderName() == null || StringUtil.isEmpty(articleUploadObs.getContent())) {
            return errorResult("参数不完整！");
        }
        String url = ObsUploadUtil.uploadTextFile(articleUploadObs.getContent(), articleUploadObs.getFolderName());
        if (StringUtil.isEmpty(url)) {
            return errorResult("存储失败！");
        }
        return successResult("存储成功！", url);
    }
}
