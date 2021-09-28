package tong.application.common.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author TR
 * @Create 2021/8/11 11:05
 * @Title: HostNameConverter.java
 * @Description: 计算机主机名称获取工具, 一般用于日志等配置项中进行本主机名称获取配置！
 */
public class HostNameConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("通过HostNameConverter获取本主机名称失败！！！"+e.getMessage());
            return "unknown";
        }
    }
}
