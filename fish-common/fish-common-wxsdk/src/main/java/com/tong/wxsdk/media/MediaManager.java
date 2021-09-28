package com.tong.wxsdk.media;

import com.google.gson.GsonBuilder;
import com.tong.wxsdk.Constant;
import com.tong.wxsdk.httputil.SendRequest;
import com.tong.wxsdk.media.result.MediaJsonRespEntity;
import com.tong.wxsdk.media.result.NewsImageJsonRespEntity;
import com.tong.wxsdk.media.sendeneity.Articles;
import com.tong.wxsdk.media.sendeneity.NewsEntity;
import com.tong.wxsdk.media.sendeneity.UpdateNewsEntity;

import java.io.File;
import java.util.List;

/**
 * 多媒体文件操作
 * 
 * @author xiayuanming
 *
 */
public class MediaManager {
	/**
	 * 上传多媒体文件永久
	 * 
	 * @param filePath
	 * @param mediaType
	 * @param isTemp
	 *            // 是否上传为临时的
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static MediaJsonRespEntity uploadMedia(String filePath, MediaType mediaType, String accessToken) throws Exception {
		// 上传为永久的
		String url = Constant.UPLOAD_MEDIA_MATERIAL_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		
		return SendRequest.uploadMedia(url, new File(filePath), mediaType, MediaJsonRespEntity.class);
	}

	/**
	 * 上传临时多媒体文件（当前微信接口里已经没有该方法的说明）
	 * @param filePath
	 * @param mediaType
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static MediaJsonRespEntity uploadMediaTemp(String filePath, MediaType mediaType, String accessToken) throws Exception {
		// 上传为临时的
		String url = Constant.UPLOAD_MEDIA_TEMP_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken).replaceAll(
				Constant.PARAM_TYPE, mediaType.getMediaType());

		return SendRequest.uploadMedia(url, new File(filePath), mediaType, MediaJsonRespEntity.class);
	}

	/**
	 * 删除多媒体文件（永久性多媒体）
	 * 
	 * @param mediaId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static MediaJsonRespEntity deleteMedia(String mediaId, String accessToken) throws Exception {
		String url = Constant.DELETE_MEDIA_MATERIAL_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = "{\"media_id\":\"" + mediaId + "\"}";

		return SendRequest.sendRequest2WeiXin4Json(url, json, MediaJsonRespEntity.class);
	}

	/**
	 * 上传永久图文素材
	 * 
	 * @param newsList
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static MediaJsonRespEntity uploadNews(List<NewsEntity> newsList, String accessToken) throws Exception {
		Articles articles = new Articles();
		articles.setArticles(newsList);

		String url = Constant.UPLOAD_NEWS_MATERIAL_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = new GsonBuilder().disableHtmlEscaping().create().toJson(articles);
		System.out.println(json);
		return SendRequest.sendRequest2WeiXin4Json(url, json, MediaJsonRespEntity.class);
	}

	/**
	 * 上传永久图文素材（当前微信接口里已经没有该方法的说明）
	 * 
	 * @param newsList
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public static MediaJsonRespEntity uploadNewsTemp(List<NewsEntity> newsList, String accessToken) throws Exception {
		Articles articles = new Articles();
		articles.setArticles(newsList);

		String url = Constant.UPLOAD_NEWS_TEMP_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = new GsonBuilder().disableHtmlEscaping().create().toJson(articles);

		return SendRequest.sendRequest2WeiXin4Json(url, json, MediaJsonRespEntity.class);
	}
	
	public static String downloadMedia(String fileSavePath, String mediaId, String accessToken) throws Exception {
		String url = Constant.DOWNLOAD_MEDIA_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken).replaceAll(Constant.PARAM_MEDIA_ID, mediaId);
		
		return SendRequest.downloadMedia(url, fileSavePath);
	}
	
	/**
	 * 修改永久图文素材
	 * 
	 * @param newsList
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static MediaJsonRespEntity updateNews(UpdateNewsEntity updateNewsEntity, String accessToken) throws Exception {

		String url = Constant.UPDATE_NEWS_MATERIAL_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		String json = new GsonBuilder().disableHtmlEscaping().create().toJson(updateNewsEntity);

		return SendRequest.sendRequest2WeiXin4Json(url, json, MediaJsonRespEntity.class);
	}
	
	/**
	 * 上传图文内图片素材，返回url
	 * @param filePath
	 * @param mediaType
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static NewsImageJsonRespEntity uploadNewsImage(String filePath, MediaType mediaType, String accessToken) throws Exception {
		// 上传为永久的
		String url = Constant.UPLOAD_NEWS_IMAGE_URL.replaceAll(Constant.PARAM_ACCESS_TOKEN, accessToken);
		
		return SendRequest.uploadMedia(url, new File(filePath), mediaType, NewsImageJsonRespEntity.class);
	}
	
}
