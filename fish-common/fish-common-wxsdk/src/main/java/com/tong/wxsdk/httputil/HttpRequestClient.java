package com.tong.wxsdk.httputil;

import com.tong.wxsdk.media.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

/**
 * http请求工具类，参照apache httpclient 4.*
 * 
 * @author jiangbo
 *
 */
public class HttpRequestClient {
	public static final String CATYPE_PKCS12 = "PKCS12";
	public static final String CHARSET_UTF8 = "utf-8";

	private HttpPost httpPost; // POST请求对象
	private HttpGet httpGet; // GET请求对象
	private SSLConnectionSocketFactory ssl; // ssl连接对象
	private StringEntity strEntity; // 请求参数（单一参数非键值对）
	private int socketTimeout = 20000; // 套接字超时
	private int connectTimeout = 20000; // 连接超时
	private int connectionRequestTimeout = 20000; // 请求超时
	private List<NameValuePair> params; // 请求参数（多参数，键值对）
	private String responseText = ""; // 请求返回文本
	private byte[] responseByte = null;
	private MultipartEntityBuilder multipartEntityBuilder;

	/**
	 * 用于重复使用该对象时清除相关实例变量
	 */
	public void clean() {
		httpPost = null;
		httpGet = null;
		ssl = null;
		strEntity = null;
		params = null;
		responseText = "";
		responseByte = null;
	}

	/**
	 * 设置POST请求
	 * 
	 * @param url
	 * @return
	 */
	public HttpRequestClient setHttpPostUrl(String url) {
		httpPost = new HttpPost(url);
		return this;
	}

	/**
	 * 设置GET请求
	 * 
	 * @param url
	 * @return
	 */
	public HttpRequestClient setHttpGetUrl(String url) {
		httpGet = new HttpGet(url);
		return this;
	}

	/**
	 * 启动https连接（公用，不带证书）
	 * 
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 */
	public HttpRequestClient useSSLRequest() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			// 信任所有
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		}).build();

		ssl = new SSLConnectionSocketFactory(sslContext);

		return this;
	}

	/**
	 * 启动https连接（带CA证书）
	 * 
	 * @param caPath
	 * @param caPwd
	 * @param caType
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public HttpRequestClient useSSLRequest(String caPath, String caPwd, String caType)
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
		return useSSLRequest(new File(caPath), caPwd, caType);
	}

	/**
	 * 启动https连接（带CA证书）
	 * 
	 * @param caFile
	 * @param caPwd
	 * @param caType
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public HttpRequestClient useSSLRequest(File caFile, String caPwd, String caType)
		throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
		KeyStore keyStore = KeyStore.getInstance(caType);
		FileInputStream instream = new FileInputStream(caFile);
		try {
			keyStore.load(instream, caPwd.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, caPwd.toCharArray()).build();
		ssl = new SSLConnectionSocketFactory(sslContext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		return this;
	}

	/**
	 * 设置单一请求参数，一般是一个对象
	 * 
	 * @param obj
	 * @param objCharSet
	 * @return
	 */
	public HttpRequestClient setStringEntity(String obj, ContentType contentType) {
		strEntity = new StringEntity(obj, contentType);
		return this;
	}

	/**
	 * 设置单一请求参数，一般是一个对象
	 * 
	 * @param obj
	 * @param objCharSet
	 * @return
	 */
	public HttpRequestClient setStringEntity(String obj, String charset) {
		strEntity = new StringEntity(obj, StringUtils.isEmpty(charset) ? CHARSET_UTF8 : charset);
		return this;
	}

	/**
	 * 设置套接字超时
	 * 
	 * @param socketTimeout
	 * @return
	 */
	public HttpRequestClient setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	/**
	 * 设置连接超时
	 * 
	 * @param connectTimeout
	 * @return
	 */
	public HttpRequestClient setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	/**
	 * 设置请求超时
	 * 
	 * @param connectionRequestTimeout
	 * @return
	 */
	public HttpRequestClient setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		return this;
	}

	/**
	 * 添加一对键值对参数
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpRequestClient addParams(String key, String value) {
		if (null == params) {
			params = new ArrayList<NameValuePair>();
		}
		params.add(new BasicNameValuePair(key, value));

		return this;
	}

	/**
	 * 添加一组键值对参数
	 * 
	 * @param map
	 * @return
	 */
	public HttpRequestClient addParams(Map<String, String> map) {
		if (null != map && map.size() > 0) {
			if (null == params) {
				params = new ArrayList<NameValuePair>();
			}

			Iterator<Entry<String, String>> it = map.entrySet().iterator();

			while (it.hasNext()) {
				Entry<String, String> entry = it.next();

				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		return this;
	}

	public HttpRequestClient addFile(String name, File file, MediaType mediaType) {
		if (null == multipartEntityBuilder) {
			multipartEntityBuilder = MultipartEntityBuilder.create();
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		}
		FileBody fileBody = new FileBody(file, ContentType.create(mediaType.getHtmlContentType(), CHARSET_UTF8), file.getName());
		multipartEntityBuilder.addPart(name, fileBody);
		return this;
	}

	/**
	 * 取请求返回文本
	 * 
	 * @return
	 */
	public String getResponseText() {
		return responseText;
	}

	public byte[] getResponseByte() {
		return responseByte;
	}

	/**
	 * 执行请求
	 * 
	 * @param reqCharset
	 * @param respCharset
	 * @return
	 * @throws Exception
	 */
	public boolean execute() throws Exception {
		return execute(null, null);
	}

	public boolean execute(String reqCharset, String respCharset) throws Exception {
		RequestConfig config = createConfig();
		CloseableHttpClient client = createHttpClient(config);

		CloseableHttpResponse response = send(client, config, reqCharset, respCharset);

		// 请求返回状态码
		boolean status = response.getStatusLine().getStatusCode() == 200;

		if (status) {
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				if (entity.getContentLength() != -1) {
					InputStream in = entity.getContent();
					byte[] buffer = new byte[1024];
					responseByte = new byte[Integer.parseInt(String.valueOf(entity.getContentLength()))];
					int readLength = 0;
					int offset = 0;
					while ((readLength = in.read(buffer)) != -1) {
						System.arraycopy(buffer, 0, responseByte, offset, readLength);
						offset += readLength;
					}
					responseText = new String(responseByte, StringUtils.isEmpty(respCharset) ? CHARSET_UTF8 : respCharset);
				} else {
					StringBuilder respText = new StringBuilder();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), StringUtils.isEmpty(respCharset) ? CHARSET_UTF8 : respCharset));
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						respText.append(text);
					}
					responseText = respText.toString();
				}

				// 释放资源和关闭底层的流
				EntityUtils.consume(entity);
			}
		}

		response.close();

		client.close();

		return status;
	}

	/**
	 * 下载
	 * 
	 * @param filePath
	 * @param reqCharset
	 * @param respCharset
	 * @return
	 * @throws Exception
	 */
	public boolean download(String filePath, String reqCharset, String respCharset) throws Exception {
		RequestConfig config = createConfig();
		CloseableHttpClient client = createHttpClient(config);

		CloseableHttpResponse response = send(client, config, reqCharset, respCharset);

		// 请求返回状态码
		boolean status = response.getStatusLine().getStatusCode() == 200;
		String fileName = "";

		if (status) {
			// 获取文件的后辍名，微信返回的http头为：Content-disposition: attachment; filename="MEDIA_ID.jpg"
			Header[] headers = response.getHeaders("Content-disposition");
			String suffix = headers[0].getValue().replaceAll("\"", "").split("\\.")[1]; // 去掉双引号，再用“.”分割取索引为1的

			// 检查文件夹是否存在
			File path = new File(filePath);
			if (!path.exists()) {
				// 不存在，则创建
				path.mkdirs();
			}

			// 得到最后的fileName
			fileName = filePath + (filePath.endsWith(File.separator) ? "" : File.separator) + new Date().getTime() + "_" + random() + "." + suffix;

			HttpEntity entity = response.getEntity();
			if (null != entity) {
				InputStream in = entity.getContent();
				File file = new File(fileName);
				FileOutputStream fout = new FileOutputStream(file);
				int ret = -1;
				byte[] tmp = new byte[1024];
				while ((ret = in.read(tmp)) != -1) {
					fout.write(tmp, 0, ret);
					// 注意这里如果用OutputStream.write(buff)的话，图片会失真
				}

				fout.flush();
				fout.close();

				// 释放资源和关闭底层的流
				EntityUtils.consume(entity);
			}
		}

		response.close();

		client.close();

		responseText = fileName;

		return status;
	}

	public byte[] download(String reqCharset, String respCharset) throws Exception {
		CloseableHttpResponse response = null;
		RequestConfig config = null;
		CloseableHttpClient client = null;

		try {
			config = createConfig();
			client = createHttpClient(config);

			response = send(client, config, reqCharset, respCharset);

			// 请求返回状态码
			boolean status = response.getStatusLine().getStatusCode() == 200;

			if (status) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					InputStream in = entity.getContent();

					byte[] buffer = new byte[1024];
					byte[] file = new byte[Integer.parseInt(String.valueOf(entity.getContentLength()))];
					int readLength = 0;
					int offset = 0;
					while ((readLength = in.read(buffer)) != -1) {
						System.arraycopy(buffer, 0, file, offset, readLength);
						offset += readLength;
					}

					return file;
					// 释放资源和关闭底层的流
					// EntityUtils.consume(entity);
				}
			}
			return null;
		} finally {
			if (null != response) {
				response.close();
			}
		}
	}

	/**
	 * （私有）生成配置
	 * 
	 * @return
	 */
	private RequestConfig createConfig() {
		// 生成超时配置对象
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		return defaultRequestConfig;
	}

	/**
	 * （私有）生成客户端
	 * 
	 * @param config
	 * @return
	 * @throws Exception
	 */
	private CloseableHttpClient createHttpClient(RequestConfig config) throws Exception {
		if (null == httpPost && null == httpGet) {
			throw new Exception("未设置请求路径！");
		}

		// 生成http实例
		HttpClientBuilder clientBuilder = HttpClients.custom();
		// 对http实例配置超时
		clientBuilder.setDefaultRequestConfig(config);

		if (null != ssl) {
			// 对http实例配置ssl用于https请求
			clientBuilder.setSSLSocketFactory(ssl);
		}

		// 生成客户端
		CloseableHttpClient client = clientBuilder.build();

		return client;
	}

	/**
	 * （私有）请求
	 * 
	 * @param client
	 * @param config
	 * @param reqCharset
	 * @param respCharset
	 * @return
	 * @throws Exception
	 */
	private CloseableHttpResponse send(CloseableHttpClient client, RequestConfig config, String reqCharset, String respCharset) throws Exception {

		CloseableHttpResponse response = null;

		RequestConfig requestConfig = RequestConfig.copy(config).build();

		if (null != httpPost) {
			if (null != strEntity && null != params && params.size() > 0 && null != multipartEntityBuilder) {
				throw new Exception("POST请求参数只能选择一种！");
			}

			if (null != strEntity) {
				httpPost.setEntity(strEntity);
			} else if (null != params && params.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(params, StringUtils.isEmpty(reqCharset) ? CHARSET_UTF8 : reqCharset));
			} else if (null != multipartEntityBuilder) {
				httpPost.setEntity(multipartEntityBuilder.build());
			}

			httpPost.setConfig(requestConfig);

			// POST请求
			response = client.execute(httpPost);
		} else {
			httpGet.setConfig(requestConfig);

			// GET请求
			response = client.execute(httpGet);
		}

		return response;
	}

	private String random() {
		Random random = new Random();

		String result = "";

		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}

		return result;
	}

	public static void main(String[] args) throws Exception {
		String ss = "34_JUAEgU-n-amsa2nvM-13A8u7mXz0juIxkGbFTF7nuTP6bNTE9_s3JdoJ55PlT1coPOdkrAB53iQEwZez-4q0uQG_OiWpgTN-GGFaDADOhqO-FH3z5QW43R0uJ_EOTPGYcZBImr4ESKd1x6KbFFJgAHAWWA";
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + ss;

		HttpRequestClient client = new HttpRequestClient();
		client.setHttpPostUrl(url).download("c:\\test\\test", "utf-8", "utf-8");

		System.out.println(client.getResponseText());

	}
}
