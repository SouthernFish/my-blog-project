package com.tong.wxsdk.httputil;

import com.google.gson.Gson;
import com.tong.wxsdk.httputil.entity.SSLEntity;
import com.tong.wxsdk.media.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 微信接口请求
 * 
 * @author jiangbo
 *
 */
public class SendRequest {
	private static Logger logger = LoggerFactory.getLogger(SendRequest.class);

	public static <T> T sendRequest2WeiXin(String url, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, null, null, null, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Json(String url, String json, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Json(url, json, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Json(String url, String json, String charset, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, json, ContentType.APPLICATION_JSON, charset, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Xml(String url, String xml, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Xml(url, xml, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4Xml(String url, String xml, String charset, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, charset, null, resultClass);
	}

	public static <T> T sendRequest2WeiXin4XmlWithSSLKey(String url, String xml, SSLEntity ssl, Class<T> resultClass) throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, null, ssl, resultClass);
	}

	public static <T> T sendRequest2WeiXin4XmlWithSSLKey(String url, String xml, String charset, SSLEntity ssl, Class<T> resultClass)
			throws Exception {
		return sendRequest2WeiXin4Text(url, xml, ContentType.APPLICATION_XML, charset, ssl, resultClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T sendRequest2WeiXin4Text(String url, String text, ContentType contentType, String charset, SSLEntity ssl, Class<T> resultClass)
			throws Exception {
		HttpRequestClient client = processClientAndBack(url, text, contentType, charset, ssl);

		String responseText = client.getResponseText();
		System.out.println("请求返回：" + responseText);

		logger.info("请求返回：" + responseText);
		// XML不能直接使用JSON转
		if (contentType != null && contentType.toString().indexOf("application/xml") != -1) {
			if (resultClass != null) {
				if (resultClass.isInstance(responseText)) {
					return resultClass.cast(responseText);
				} else {
					throw new RuntimeException(responseText + " is not a " + resultClass.getName());
				}
			}
		}

		if (String.class.equals(resultClass)) {
			return (T) responseText;
		} else {
			return new Gson().fromJson(responseText, resultClass);
		}
	}
	
	public static byte[] sendRequest2WeiXin4JsonBackByteArray(String url, String json) throws Exception {
		return sendRequest2WeiXin4JsonBackByteArray(url, json, ContentType.APPLICATION_JSON, "utf-8", null);
	}
	
	public static byte[] sendRequest2WeiXin4JsonBackByteArray(String url, String text, ContentType contentType, String charset, SSLEntity ssl)
			throws Exception {
		HttpRequestClient client = processClientAndBack(url, text, contentType, charset, ssl);

		return client.getResponseByte();
	}
	
	private static HttpRequestClient processClientAndBack(String url, String text, ContentType contentType, String charset, SSLEntity ssl) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			if (null == ssl) {
				client.useSSLRequest();
			} else {
				client.useSSLRequest(ssl.getFilePath(), ssl.getPassword(), ssl.getCaType());
			}
		}
		if (!StringUtils.isEmpty(text)) {
			contentType = null == contentType ? ContentType.DEFAULT_TEXT : contentType;
			contentType = StringUtils.isEmpty(charset) ? contentType : contentType.withCharset(charset);

			client.setStringEntity(text, contentType);
		}
		client.execute();
		
		return client;
	}

	public static <T> T uploadMedia(String url, File file, MediaType mediaType, Class<T> resultClass) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			client.useSSLRequest();
		}
		client.addFile("media", file, mediaType).execute();

		logger.info("请求返回：" + client.getResponseText());
		System.out.println("请求返回：" + client.getResponseText());

		return new Gson().fromJson(client.getResponseText(), resultClass);
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @param url
	 * @param fileSavePath
	 * @return 返回文件保存本地的完整路径
	 * @throws Exception
	 */
	public static String downloadMedia(String url, String fileSavePath) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpPostUrl(url);
		if (url.startsWith("https")) {
			client.useSSLRequest();
		}

		client.download(fileSavePath, "utf-8", "utf-8");

		return client.getResponseText();
	}
	
	public static byte[] downloadQRCode(String url) throws Exception {
		HttpRequestClient client = new HttpRequestClient();

		client.setHttpGetUrl(url);
		if (url.startsWith("https")) {
			client.useSSLRequest();
		}

		return client.download("utf-8", "utf-8");
	}
}
