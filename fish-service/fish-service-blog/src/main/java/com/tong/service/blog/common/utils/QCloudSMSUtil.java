package com.tong.service.blog.common.utils;

import com.google.gson.Gson;
import com.tong.service.blog.common.Constant;
import com.tong.entity.sms.SMSParameter;
import com.tong.entity.sms.SMSRecvEntity;
import com.tong.entity.sms.SMSSendEntity;
import com.tong.enums.sms.SMSSignEnum;
import com.tong.enums.sms.SMSTempletEnum;
import com.tong.wxsdk.httputil.HttpRequestClient;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 腾讯云短信发送服务类
 * 
 * @author Jiangbo
 *
 */
public class QCloudSMSUtil {
	private final static String RECV_SUCCESS = "0";

	private String lastErrorText;
	private String msg;

	/**
	 * 获取最后一次错误信息
	 * 
	 * @return
	 */
	public String getLastErrorText() {
		return lastErrorText;
	}

	public String getMsg() {
		return msg;
	}

	/**
	 * 发送短信
	 * 
	 * @param toUserTel 接收短信的用户手机号码
	 * @param sign      签名
	 * @param signFront 签名前置
	 * @param extend    扩展信息
	 * @param template   短信模板
	 * @param smsParams 模板参数
	 * @return
	 */
	public boolean sendSMS(String toUserTel, SMSSignEnum sign, boolean signFront, String extend, SMSTempletEnum template, SMSParameter smsParams) {
		try {
			// 得到腾讯云发送短信的文本内容
			// 判断该短信是否是国际短信，如果是，则使用繁体字短信（港澳台地区）香港852，澳门853，886
			String templateContent = "";
			String signName = "";
			String nationCode = "86";

			// 是否发送繁体字短信（国际号且存在繁体模板
			boolean needTraditionalTemplate = (toUserTel.startsWith("852") || toUserTel.startsWith("853") || toUserTel.startsWith("886")) && StringUtils.isNotBlank(template.getTencentTCTemplet());

			if (needTraditionalTemplate) {
				templateContent = template.getTencentTCTemplet();
				signName = sign.getSignNameTC();
				nationCode = toUserTel.substring(0, 3);
				toUserTel = toUserTel.substring(3); // 港澳台地区电话是按照【国家码】+【手机号】存的。
			} else {
				templateContent = template.getTencentTemplet();
				signName = sign.getSignName();
			}

			String content = "";
			if (null != smsParams && smsParams.size() > 0) {
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < smsParams.size(); i++) {
					list.add(smsParams.get(i).getValue());
				}

				content = String.format(templateContent, list.toArray());
			} else {
				content = templateContent;
			}

			if (needTraditionalTemplate) {
				content = ZHConvertUtils.simplified2Traditional(content);
			}

			// 去掉正文中的【】
			content = content.replaceAll("【", "").replaceAll("】", "");

			if (signFront) {
				msg = signName + content;
			} else {
				msg = content + signName;
			}

			Gson gson = new Gson();

			SMSSendEntity sendEntity = SMSSendEntity.getInstance(nationCode, toUserTel, msg, extend, template.getqCloudAPIInfo().getApiKey());
			String json = gson.toJson(sendEntity);

			String url = String.format(Constant.API_URL_SMS, template.getqCloudAPIInfo().getApiId(), new Random().nextInt());

			System.out.println("发送的URL：" + url);
			System.out.println("发送的文本：" + json);

			HttpRequestClient httpClient = new HttpRequestClient().setHttpPostUrl(url).setStringEntity(json, "utf-8");
			httpClient.execute();
			String resultJson = httpClient.getResponseText();

			System.out.println("返回的文本：" + resultJson);

			if (StringUtils.isEmpty(resultJson)) {
				lastErrorText = "未接收到远端返回结果";
				return false;
			} else {
				SMSRecvEntity recvEntity = gson.fromJson(resultJson, SMSRecvEntity.class);
				if (recvEntity.getResult().equals(RECV_SUCCESS)) {
					return true;
				} else {
					lastErrorText = recvEntity.getErrmsg();
					return false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}
}
