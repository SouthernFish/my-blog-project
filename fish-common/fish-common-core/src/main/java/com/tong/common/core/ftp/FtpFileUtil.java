package com.tong.common.core.ftp;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author TR
 * @Create 2021/8/29 13:41
 * @Title: FtpFileUtil
 * @Description: ftp形式文件操作工具类
 */
public class FtpFileUtil {

    public FtpFileUtil() {}
    /**
     * @Author TR
     * @Create 2021/8/29 14:10
     * @Title: uploadFile
     * @Description: 文件上传
     * @Params: remoteBasePath 服务器基础路径；filePath 图片写入目录；allFileName 文件名(包含后缀)；inputStream 文件输入流
     */
    public static String uploadFile(String remoteBasePath, String filePath, String allFileName, InputStream inputStream) {
        try {
            String result = null; // 返回存储的全路径，不包含服务器地址
            FTPClient ftpClient = FtpConfig.getFtpClient();
            if (ftpClient == null) { // 连接失败
                return result;
            }
            // 切换到上传目录: 按日期存储，日期文件夹filePath不存在则创建
            if (!ftpClient.changeWorkingDirectory(remoteBasePath + filePath)) {
                // filePath不存在 则创建
                if (!ftpClient.makeDirectory(remoteBasePath + filePath)) {
                    return result;
                } else {
                    ftpClient.changeWorkingDirectory(remoteBasePath + filePath);
                }
            }
            // 远程文件名: 建议不要包含中文， 如果需要支持中文，转码： new string(allFileName.getBytes( "GBK")，"iso-8859-1")
            String fileName = new String(allFileName.getBytes("UTF-8"), "iso-8859-1");
            System.out.println("============文件开始上传============");
            // 上传
//            boolean flag = ftpClient.storeFile(fileName, inputStream);
            if(ftpClient.storeFile(allFileName, inputStream)){
                result = filePath + "/" + allFileName;
                System.out.println("文件上传成功");
            } else {
                System.out.println("文件上传失败");
            }
            //关闭连接
            ftpClient.logout();
            ftpClient.disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
