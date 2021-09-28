package com.tong.common.core.config;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 描述: 计算机主机名称获取工具<br/>
 * 本工具一般用于日志等配置项中进行本主机名称获取配置！
 *
 * @author Ajian
 * @date 2021年04月21日 16:42
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
