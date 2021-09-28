package com.tong.enums.sms;

/**
 * 腾讯云短信服务，短信签名
 * 
 * @author: chentianjin
 * @date: 2021年3月15日 下午7:41:47
 */
public enum SMSSignEnum {
	悦家云("【悦家云】", "【悅家雲】", "悦家云");

	private String signName;
	private String signNameTC;
	private String aliSignName;

	SMSSignEnum(String signName, String signNameTC, String aliSignName) {
		this.signName = signName;
		this.signNameTC = signNameTC;
		this.aliSignName = aliSignName;
	}

	public String getSignNameTC() {
		return signNameTC;
	}

	public void setSignNameTC(String signNameTC) {
		this.signNameTC = signNameTC;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getAliSignName() {
		return aliSignName;
	}

	public void setAliSignName(String aliSignName) {
		this.aliSignName = aliSignName;
	}
}
