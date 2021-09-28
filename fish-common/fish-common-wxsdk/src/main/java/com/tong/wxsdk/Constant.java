package com.tong.wxsdk;

/**
 * 微信接口地址常量
 * 
 * @author Administrator
 *
 */
public class Constant {
	public final static String PARAM_APPID = "APPID";
	public final static String PARAM_APPSECRET = "APPSECRET";
	public final static String PARAM_ACCESS_TOKEN = "ACCESS_TOKEN";
	public final static String PARAM_OPENID = "OPENID";
	public final static String PARAM_NEXT_OPENID = "NEXT_OPENID";
	public final static String PARAM_TYPE = "TYPE";
	public final static String PARAM_MEDIA_ID = "MEDIA_ID";
	public final static String PARAM_TICKET = "TICKET";

	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	// JSAPI_TICKET获取
	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	// API_TICKET获取
	public final static String API_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";

	/******************************************** 菜单操作 *****************************************/
	// 菜单创建（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 清除菜单
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/******************************************** 菜单操作 *****************************************/

	/******************************************** 多媒体操作 ***************************************/
	// 上传永久多媒体文件
	public final static String UPLOAD_MEDIA_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	// 上传临时多媒体文件（3天）
	public final static String UPLOAD_MEDIA_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	// 删除永久多媒体文件
	public final static String DELETE_MEDIA_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	// 新增永久图文素材
	public final static String UPLOAD_NEWS_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	// 上传图文消息内的图片获取URL
	public final static String UPLOAD_NEWS_IMAGE_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

	public final static String UPDATE_NEWS_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	// 新增临时图文素材
	public final static String UPLOAD_NEWS_TEMP_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	// 下载多媒体素材
	public final static String DOWNLOAD_MEDIA_URL = "https://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	/******************************************** 多媒体操作 ***************************************/

	/******************************************** 发送消息 *****************************************/
	// 群发消息
	public static final String MSG_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	// 发送客服消息接口
	public static final String MSG_SERVER_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 预览
	public static final String MSG_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	// 客服回复文本消息
	public static final String MSG_TEXT_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	// 发送模板消息post
	public static final String TEMPLATE_SEND = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	// 获取模板消息ID
	public static final String GET_TEMPLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	// 删除已发送的消息
	public static final String MSG_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";

	/******************************************** 发送消息 *****************************************/

	/******************************************** 订阅消息 *****************************************/
	// 发送消息
	public static final String MINIPROGRAM_SEND_SUBSCRIBE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
	// 公众号发送订阅通知
	public static final String BIZSEND_SUBSCRIBE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/subscribe/bizsend?access_token=ACCESS_TOKEN";

	/******************************************** 小程序订阅消息 *****************************************/

	/******************************************** 带参数的二维码 *****************************************/
	// 创建二维码
	public static final String CREATE_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	/** 创建小程序码(码数量极多的业务场景，永久有效，数量暂无限制) */
	public static final String CREATE_WXA_UNLIMIT_CODE = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN";
	/** 创建小程序码(码数量极多的业务场景，永久有效，有数量限制) */
	public static final String CREATE_WXAPP_CODE = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN";
	// 下载二维码
	public static final String DOWNLOAD_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + PARAM_TICKET;
	/******************************************** 带参数的二维码 *****************************************/

	/**
	 * 统计功能
	 */
	// 获取用户增减数据（getusersummary）
	public static final String GET_USER_SUMMARY_URL = "https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN";
	// 获取累计用户数据（getusercumulate）
	public static final String GET_USER_CUMULATE_URL = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN";
	// 获取图文群发每日数据（getarticlesummary）
	public static final String GET_ARTICLE_SUMMARY_URL = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN";
	// 获取图文群发总数据（getarticletotal）
	public static final String GET_ARTICLE_TOTAL_URL = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN";
	// 获取图文统计数据（getuserread）
	public static final String GET_USER_READ_URL = "https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN";
	// 获取图文统计分时数据（getuserreadhour）
	public static final String GET_USER_READ_HOUR_URL = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN";
	// 获取图文分享转发数据（getusershare）
	public static final String GET_USER_SHARE_URL = "https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN";
	// 获取图文分享转发分时数据（getusersharehour）
	public static final String GET_USER_SHARE_HOUR_URL = "https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN";

	/**
	 * OAuth2.0网页授权
	 */
	// 用户同意授权，获取code
	public static final String GET_OAUTH2_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	// 悦家认证地址（正式）
	public static final String GET_YJ_PRODUCT_AUTH_PATH = "/authcenter/wap/wx?terminalTypeId=%s&wxConfigId=%s&callBackUrl=%s&route=%s";
	// 悦家静默认证地址（正式）
	public static final String AUTHCENTER_WX_SILENT_PATH = "/authcenter/wap/wx/silent?terminalTypeId=%s&wxConfigId=%s&callBackUrl=%s&route=%s";

	// 人脸资料录入地址
	public static final String GET_FACE_IMPORT_PATH = "/yj/face/index?buildingProjectId=%s&qrcodeId=%s";

	// 悦家认证地址（测式）
	// public static final String GET_YJ_TEST_AUTH_URL =
	// "http://prwap.hejuzg.cn/ww/authcenter/wap/wx?terminalTypeId=%s&wxConfigId=%s&callBackUrl=%s&route=%s";

	// 通过code获取授权access_token
	public static final String GET_ACCESSTOKEN_BYCODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	// 刷新access_token
	public static final String REFRESH_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";

	// 拉取用户信息(需scope为 snsapi_userinfo)
	public static final String GET_USERINFO_BYTOKEN_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	/******************************************** 微信用户管理 *****************************************/
	// 获取关注者列表
	public static final String GET_ALLUSER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	// 查询所有分组
	public static final String GET_ALLGROUP_LIST_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
	// 创建分组(标签管理)
	public static final String CREATE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
	// 获取公众号已创建的标签
	public static final String GET_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
	// 修改分组 ( 编辑标签)
	public static final String MODIFY_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
	// 删除分组
	public static final String DELETE_TAG_URL = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
	// 获取标签下粉丝列表
	public static final String GET_FANS_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
	// 批量添加分组
	public static final String BATCH_TAGGING_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
	// 批量取消分组
	public static final String BATCH_UN_TAGGING_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
	// 获取用户身上的标签列表
	public static final String USER_GET_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
	// 设置用户备注名
	public static final String USER_NOTE_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	// 获取用户所在组
	public static final String GET_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
	// 获取单个用户信息
	public static final String USER_GET_USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 批量获取用户基本信息
	public static final String BATCH_GET_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	// 黑名单管理
	public static final String USER_BLACK_LIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN";
	// 拉黑用户
	public static final String USER_BLACK_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN";
	// 取消拉黑用户
	public static final String USER_AWAY_BLACK_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN";

	/******************************************** 微信数据统计 *****************************************/
	// 获取用户增减数据
	public static final String DATA_USERSUMMARY_URL = "https://api.weixin.qq.com/datacube/getusersummary?access_token=ACCESS_TOKEN";
	// 获取用户累计数据
	public static final String DATA_USERCUMULATE_URL = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN";
	// 获取图文群发每日数据
	public static final String DATA_ARTICLESUMMARY_URL = "https://api.weixin.qq.com/datacube/getarticlesummary?access_token=ACCESS_TOKEN";
	// 获取图文群发总数据
	public static final String DATA_ARTICLETOTAL_URL = "https://api.weixin.qq.com/datacube/getarticletotal?access_token=ACCESS_TOKEN";
	// 获取图文统计数据
	public static final String DATA_USERREAD_URL = "https://api.weixin.qq.com/datacube/getuserread?access_token=ACCESS_TOKEN";
	// 获取图文统计分时数据
	public static final String DATA_USERREADHOUR_URL = "https://api.weixin.qq.com/datacube/getuserreadhour?access_token=ACCESS_TOKEN";
	// 获取图文分享转发数据
	public static final String DATA_USERSHARE_URL = "https://api.weixin.qq.com/datacube/getusershare?access_token=ACCESS_TOKEN";
	// 获取图文分享转发分时数据
	public static final String DATA_USERSHAREHOUR_URL = "https://api.weixin.qq.com/datacube/getusersharehour?access_token=ACCESS_TOKEN";
	// 获取消息发送概况数据
	public static final String DATA_UPSTREAMMSG_URL = "https://api.weixin.qq.com/datacube/getupstreammsg?access_token=ACCESS_TOKEN";
	// 获取消息发送分时数据
	public static final String DATA_UPSTREAMMSGHOUR_URL = "https://api.weixin.qq.com/datacube/getupstreammsghour?access_token=ACCESS_TOKEN";
	// 获取消息发送周数据
	public static final String DATA_UPSTREAMMSGWEEK_URL = "https://api.weixin.qq.com/datacube/getupstreammsgweek?access_token=ACCESS_TOKEN";
	// 获取消息发送月数据
	public static final String DATA_UPSTREAMMSGMONTH_URL = "https://api.weixin.qq.com/datacube/getupstreammsgmonth?access_token=ACCESS_TOKEN";
	// 获取消息发送分布数据
	public static final String DATA_UPSTREAMMSGDIST_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdist?access_token=ACCESS_TOKEN";
	// 获取消息发送分布周数据
	public static final String DATA_UPSTREAMMSGDISTWEEK_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdistweek?access_token=ACCESS_TOKEN";
	// 获取消息发送分布月数据
	public static final String DATA_UPSTREAMMSGDISTMONTH_URL = "https://api.weixin.qq.com/datacube/getupstreammsgdistmonth?access_token=ACCESS_TOKEN";
	// 获取接口分析数据
	public static final String DATA_INTERFACESUMMARY_URL = "https://api.weixin.qq.com/datacube/getinterfacesummary?access_token=ACCESS_TOKEN";
	// 获取接口分析分时数据
	public static final String DATA_INTERFACESUMMARYHOUR_URL = "https://api.weixin.qq.com/datacube/getinterfacesummaryhour?access_token=ACCESS_TOKEN";

	/******************************************** 图文消息留言管理接口URL *****************************************/
	// 打开已群发文章评论
	public static final String NEWS_COMMENT_OPEN = "https://api.weixin.qq.com/cgi-bin/comment/open?access_token=ACCESS_TOKEN";
	// 关闭已群发文章评论
	public static final String NEWS_COMMENT_CLOSE = "https://api.weixin.qq.com/cgi-bin/comment/close?access_token=ACCESS_TOKEN";
	// 查看指定文章的评论数据
	public static final String NEWS_COMMENT_LIST = "https://api.weixin.qq.com/cgi-bin/comment/list?access_token=ACCESS_TOKEN";
	// 将评论标记精选
	public static final String NEWS_COMMENT_MARKELECT = "https://api.weixin.qq.com/cgi-bin/comment/markelect?access_token=ACCESS_TOKEN";
	// 将评论取消精选
	public static final String NEWS_COMMENT_UNMARKELECT = "https://api.weixin.qq.com/cgi-bin/comment/unmarkelect?access_token=ACCESS_TOKEN";
	// 删除评论
	public static final String NEWS_COMMENT_DELETE = "https://api.weixin.qq.com/cgi-bin/comment/delete?access_token=ACCESS_TOKEN";
	// 回复评论
	public static final String NEWS_COMMENT_REPLY = "https://api.weixin.qq.com/cgi-bin/comment/reply/add?access_token=ACCESS_TOKEN";
	// 删除回复
	public static final String NEWS_COMMENT_REPLY_DELETE = "https://api.weixin.qq.com/cgi-bin/comment/reply/delete?access_token=ACCESS_TOKEN";

	/******************************************** 微信消息类型 *****************************************/
	public static final String MSGTYPE_TEXT = "text";
	public static final String MSGTYPE_IMAGE = "image";
	public static final String MSGTYPE_VOICE = "voice";
	public static final String MSGTYPE_VIDEO = "video";
	public static final String MSGTYPE_SHORTVIDEO = "shortvideo"; // 视频
	public static final String MSGTYPE_LOCATION = "location"; // 位置信息
	public static final String MSGTYPE_LINK = "link"; // 链接
	public static final String MSGTYPE_EVENT = "event"; // 事件
	/******************************************** 微信消息类型 *****************************************/

	/******************************************** 微信事件类型 *****************************************/
	public static final String EVENTTYPE_SUBSCRIBE = "subscribe"; // 关注
	public static final String EVENTTYPE_UNSUBSCRIBE = "unsubscribe"; // 取消关注
	public static final String EVENTTYPE_SCAN = "SCAN"; // 已经关注
	public static final String EVENTTYPE_LOCATION = "LOCATION"; // 上报地理位置事件
	public static final String EVENTTYPE_CLICK = "CLICK"; // 菜单点击
	public static final String EVENTTYPE_VIEW = "VIEW"; // 菜单点击跳转连接
	public static final String EVENTTYPE_SCANCODE_PUSH = "scancode_push"; // 扫码推事件的事件推送
	public static final String EVENTTYPE_SCANCODE_WAITMSG = "scancode_waitmsg"; // 扫码推事件且弹出“消息接收中”提示框的事件推送
	public static final String EVENTTYPE_PIC_SYSPHOTO = "pic_sysphoto"; // 弹出系统拍照发图的事件推送
	public static final String EVENTTYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album"; // 弹出拍照或者相册发图的事件推送
	public static final String EVENTTYPE_PIC_WEIXIN = "pic_weixin"; // 弹出微信相册发图器的事件推送
	public static final String EVENTTYPE_LOCATION_SELECT = "location_select"; // 弹出地理位置选择器的事件推送
	/******************************************** 微信事件类型 *****************************************/

	/******************************************** 微信支付 *****************************************/
	/** 发放普通红包 */
	public static final String SEND_REDPACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	/** 发放裂变红包 */
	public static final String SEND_GROUP_REDPACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack";
	/** 查询红包记录 */
	public static final String GET_HB_INFO = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gethbinfo";

	/******************************************** 微信网页开发相关 *************************************/
	public static final String OTHER_PARAM = "other_param";
}
