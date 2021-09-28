package com.tong.enums.sms;

/**
 * @Author TR
 * @Create 2021/8/11 9:07
 * @Title: QCloudAPIInfoEnum.java
 * @Description: 短信机配置
 */
public enum QCloudAPIInfoEnum {
	悦家云短信通道("1400014254", "ef5c4072909ab6233a649fa1d01216ea");

	private String apiId;
	private String apiKey;

	QCloudAPIInfoEnum(String apiId, String apiKey) {
		this.apiId = apiId;
		this.apiKey = apiKey;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
