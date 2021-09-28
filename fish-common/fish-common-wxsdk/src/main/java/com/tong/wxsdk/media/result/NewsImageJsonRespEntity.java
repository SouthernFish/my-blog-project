package com.tong.wxsdk.media.result;

import com.tong.wxsdk.result.BaseJsonRespEntity;

public class NewsImageJsonRespEntity extends BaseJsonRespEntity {
	private static final long serialVersionUID = 1760570449596220537L;
	private String media_id;
	private String url;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
