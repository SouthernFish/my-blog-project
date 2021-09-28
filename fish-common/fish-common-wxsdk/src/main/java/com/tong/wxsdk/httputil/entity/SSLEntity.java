package com.tong.wxsdk.httputil.entity;

import java.io.Serializable;

/**
 * SSL证书实体
 * @author jiangbo
 *
 */
public class SSLEntity implements Serializable {
	private static final long serialVersionUID = -1745940146999006839L;
	private String filePath; // 证书路径
	private String password; // 密码
	private String caType; // 证书类型

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaType() {
		return caType;
	}

	public void setCaType(String caType) {
		this.caType = caType;
	}
}
