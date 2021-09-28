package com.tong.service.blog.common.utils;

import com.tong.common.core.obs.ObsFileUtil;
import com.tong.common.core.obs.obsEnum.BucketEnum;
import com.tong.common.core.util.ClassPathUtil;
import com.tong.common.core.util.DateUtil;
import com.tong.common.core.util.MD5Utils;
import com.tong.common.core.util.ProFileReader;
import com.tong.enums.ObsPathEnums;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

public class ObsUploadUtil {

    public static String UPLOAD_SUB_FOLDER = "";

    static {
        File file = new File(ClassPathUtil.getPath() + "resource/system.properties");
        ProFileReader uploadPropFile;
        try {
            uploadPropFile = new ProFileReader(new FileInputStream(file));
            UPLOAD_SUB_FOLDER = uploadPropFile.getParamValue("UPLOAD_SUB_FOLDER");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author TR
     * @Create 2021/8/15 18:16
     * @Title: uploadByMultipartFile
     * @Description: springMVC上传图片,返回平台可访问的http图片url
     */
    public static String uploadImage(MultipartFile source) throws IllegalStateException, IOException {

        String today = DateUtil.currentDate();
        int random = (int) (Math.random() * 10000);

        String srcFileName = source.getOriginalFilename();
        // 获取文件后辍名
        String suffix = "jpg";
        String[] fileProperties = srcFileName.split("\\.");
        if (fileProperties.length >= 2) {
            suffix = fileProperties[fileProperties.length - 1];
        }
        // 拼接文件名字
        String fileName = System.nanoTime() + "_" + random + "." + suffix;

        String resultObs = ObsPathEnums.图片.getPath() + "/" + UPLOAD_SUB_FOLDER + "/" + today + "/";

        // 上传到OBS服务器
        String OBS_FILE_PATH = ObsFileUtil.uploadStream(BucketEnum.深蓝专用桶, source.getBytes(), resultObs + fileName);

        return OBS_FILE_PATH;
    }

    /**
     * @Author TR
     * @Create 2021/8/15 18:15
     * @Title: uploadHtmlFile
     * @param: file  文件
     * @param: folderName 文件夹地址
     * @Description: md文本内容上传到obs中存储为一个md文件
     */
    public static String uploadTextFile(String content, String folderName) {
        //取文本内容的摘要字符串为文件名称
        String fileName = UUID.randomUUID() + "-" + MD5Utils.Md5(content) + ".md";
        String resultObs = ObsPathEnums.文章.getPath() + "/" + UPLOAD_SUB_FOLDER + "/folder_" + folderName + "/" + DateUtil.currentDate() + "/";
        String OBS_FILE_PATH = null;
        try {
            OBS_FILE_PATH = ObsFileUtil.uploadStream(BucketEnum.深蓝专用桶, content.getBytes("UTF-8"), resultObs + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OBS_FILE_PATH;
    }

    /**
     * @Author TR
     * @Create 2021/8/15 18:17
     * @Title: uploadVideo
     * @param: file  文件
     * @param: folderName 文件夹地址
     * @Description: 上传本地视频到obs
     */
    public static String uploadVideo(MultipartFile file){

        String contentType = file.getContentType();

        String fileName = UUID.randomUUID() + "."+contentType;

        String  resultObs = ObsPathEnums.视频.getPath() + "/" + UPLOAD_SUB_FOLDER + "/" + DateUtil.currentDate() + "/";

        String OBS_FILE_PATH = null;
        try {
            OBS_FILE_PATH = ObsFileUtil.uploadStream(BucketEnum.深蓝专用桶,file.getBytes(),resultObs+fileName);

        }catch (Exception e){
            e.printStackTrace();
        }

        return  OBS_FILE_PATH;
    }
    }
