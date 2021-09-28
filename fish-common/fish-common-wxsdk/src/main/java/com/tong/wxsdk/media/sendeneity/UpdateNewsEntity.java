package com.tong.wxsdk.media.sendeneity;

public class UpdateNewsEntity {
	private String media_id;
	private Integer index;
	private NewsEntity articles;

	public Integer getIndex() {
		return index;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public NewsEntity getNewsEntity() {
		return articles;
	}

	public void setNewsEntity(NewsEntity newsEntity) {
		this.articles = newsEntity;
	}

}
