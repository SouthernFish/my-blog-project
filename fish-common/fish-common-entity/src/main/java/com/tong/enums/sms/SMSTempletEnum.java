package com.tong.enums.sms;

/**
 * 短信模板
 * 
 * @author: chentianjin
 * @date: 2021年3月15日 下午7:41:17
 */
public enum SMSTempletEnum {
	公共验证码(QCloudAPIInfoEnum.悦家云短信通道, "欢迎关注悦家云，您的服务验证码为%s，悦家期待更好地为您服务。", "歡迎關注悅家雲，您的服務驗證碼為%s，悅家期待更好地為您服務。", "SMS_104395002");

	private String tencentTemplet; // 简体
	private String tencentTCTemplet; // 繁体
	private String aliTemplet;
	private QCloudAPIInfoEnum qCloudAPIInfo;

	/**
	 * 模板实体
	 *
	 * @param qCloudAPIInfo  腾讯云短信通道信息
	 * @param tencentTemplet 腾讯短信模板
	 * @param aliTemplet     阿里短信模板
	 */
	SMSTempletEnum(QCloudAPIInfoEnum qCloudAPIInfo, String tencentTemplet, String tencentTCTemplet, String aliTemplet) {
		this.tencentTemplet = tencentTemplet;
		this.tencentTCTemplet = tencentTCTemplet;
		this.aliTemplet = aliTemplet;
		this.qCloudAPIInfo = qCloudAPIInfo;
	}

	public String getTencentTemplet() {
		return tencentTemplet;
	}

	public String getTencentTCTemplet() {
		return tencentTCTemplet;
	}

	public String getAliTemplet() {
		return aliTemplet;
	}

	public QCloudAPIInfoEnum getqCloudAPIInfo() {
		return qCloudAPIInfo;
	}
}