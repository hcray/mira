package com.common;

public class MiraConstants {
	
	/**
	 * 编码
	 */
	public final static  String charset = "UTF-8"; 
	
	/**
	 * 服务端地址
	 */
	public final static String baseUrl = "http://mira.api.1088pm.cn/";
	
	/**
	 * 创建Token
	 */
	public final static String createToken = baseUrl + "/api/User/CreateToken";
	
	/**
	 * 用户登录
	 */
	public final static String userLogin = baseUrl + "/api/User/Login";
	
	/**
	 * 获取验证码
	 */
	public final static String getVerificationCode = baseUrl + "/api/User/GetVerificationCode";
	
	/**
	 * 修改个人信息
	 */
	public final static String updateUserInfo = baseUrl + "/api/User/UpdateUserInfo";
	
	/**
	 * 修改密码
	 */
	public final static String changePassword = baseUrl + "/api/User/ChangePassword";
	
	/**
	 * 获取皮肤类型小类
	 */
	public final static String getSkinList = baseUrl + "/api/User/GetSkinList";
	
	/**
	 * 获取敏感类型小类
	 */
	public final static String getSensitiveList = baseUrl + "/api/User/GetSensitiveList";

	/**
	 * 上传图片接口
	 */
	public final static String uploadImageByte = baseUrl + "/api/User/UploadImageByte";

	/**
	 * 上传图片信息接口
	 */
	public final static String uploadImage = baseUrl + "/api/User/UploadImage";

	/**
	 * 上传检测数据
	 */
	public final static String uploadDetection = baseUrl + "/api/User/UploadDetection";

	/**
	 * 下载检测数据
	 */
	public final static String getDetection = baseUrl + "/api/User/GetDetection";

	/**
	 * 上传日志接口
	 */
	public final static String uploadNotice = baseUrl + "/api/User/UploadNotice";
	
	/**
	 * 部位
	 */
	public final static String PART = "part";
	
	/**
	 * 1：额头
	 */
	public final static int PART_HEAD = 1;
	
	/**
	 * 2：脸颊 
	 */
	public final static int PART_FACE = 2;
	
	/**
	 * 3：鼻子
	 */
	public final static int PART_NOSE = 3;
	
	/**
	 * 4：下巴
	 */
	public final static int PART_CHIN = 4;
	
	/**
	 * 已经选择的城市 
	 */
	public final static String SELECTED_CITY = "selectedCity"; 
}
