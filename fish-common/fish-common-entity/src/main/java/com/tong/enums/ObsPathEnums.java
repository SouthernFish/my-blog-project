package com.tong.enums;

/**
 * @Author TR
 * @Create 2021/8/15 18:08
 * @Title: ObsPathEnums
 * @Description: OBS路径枚举
 */
public enum ObsPathEnums {
	静态资源基础路径("/app/static-resources"),
	图片("/static-img-tong"),
	文章("/static-article-tong"),
	视频("/static-video-tong");


	private String path;

	private ObsPathEnums(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
