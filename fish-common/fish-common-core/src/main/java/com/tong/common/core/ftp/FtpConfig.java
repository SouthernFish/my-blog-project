package com.tong.common.core.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * @Author TR
 * @Create 2021/8/29 13:19
 * @Title: FtpConfig.java
 * @Description: ftp配置
 */
public class FtpConfig {

    //ftp服务器地址
    public static final String hostname = "121.41.1.176";
    //ftp服务器端口号默认为21
    private static final Integer port = 21;
    // ftp登录账号
    private static final  String username = "fish";
    //ftp登录密码
    private static final  String password = "netstat-ano|findstr8080";

    public FtpConfig() {

    }

    public static FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(hostname, port); // 创建ftp连接
            ftpClient.login(username, password); // 登录ftp

            int replyCode = ftpClient.getReplyCode();  // 判断登录清情况
            System.out.println("判断登录清情况: " + replyCode);
            ftpClient.setDataTimeout(20000); // 超时设置
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 修改上传文件的格式： 二进制
            ftpClient.enterLocalPassiveMode(); //
            if(!FTPReply.isPositiveCompletion(replyCode)){  // 判断连接
                System.out.println("FTP连接失败");
                ftpClient.disconnect();
                return null;
            }else {
                System.out.println("FTP连接成功");
                return ftpClient;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }




    }
}
