package com.tong.service.blog.common.utils;

import com.tong.common.core.ftp.FtpFileUtil;
import com.tong.common.core.util.DateUtil;
import com.tong.enums.ObsPathEnums;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FtpUploadUtil {
    /**
     * @Author TR
     * @Create 2021/8/15 18:16
     * @Title: uploadByMultipartFile
     * @Description: springMVC上传图片,返回平台可访问的http图片url
     */
    public static String uploadFile(MultipartFile source) throws IllegalStateException, IOException {

        // 文件名
        String srcFileName = source.getOriginalFilename();
        // 获取文件后辍名
        String suffix = "jpg";
        String[] fileProperties = srcFileName.split("\\.");
        if (fileProperties.length >= 2) {
            suffix = fileProperties[fileProperties.length - 1];
        }
        // 生成随机数
        int random = (int) (Math.random() * 10000);
        // 服务器端文档名: 拼接,重写文件名字
        String fileName = System.nanoTime() + "_" + random + "." + suffix;

        // 服务器静态资源路径：上传基础位置
        String remoteBasePath = ObsPathEnums.静态资源基础路径.getPath();
        // 服务器上目标文件夹（日期文件夹）,前面拼接类型路径
        String filePath = ObsPathEnums.图片.getPath() + "/" +  DateUtil.currentDate();

        /* 文件流
         * 如果给定了文件绝对地址 可以使用如下创建，其中 originFileName 是文件绝对地址
         * InputStream inputStream = new FileInputStream( new File(originFileName));
         * */
        InputStream inputStream = source.getInputStream();
        // 上传到FTP服务器
        String FTP_FILE_PATH = FtpFileUtil.uploadFile(remoteBasePath, filePath, fileName, inputStream);
        return FTP_FILE_PATH;
    }

}
