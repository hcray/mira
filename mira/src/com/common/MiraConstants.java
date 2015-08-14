package com.common;

public class MiraConstants {
	
	/**
	 * 编码
	 */
	public final static  String charset = "UTF-8"; 
	
	/**
	 * 服务端地址
	 */
	public final static String baseUrl = "";
	
	/**
	 * 创建Token
	 */
	public final static String createToken = "http://" + baseUrl + "/api/User/CreateToken";
	
	/**
	 * 用户登录
	 */
	public final static String userLogin = "http://" + baseUrl + "/api/User/Login";
	
	/**
	 * 获取验证码
	 */
	public final static String getVerificationCode = "http://" + baseUrl + "/api/User/GetVerificationCode";
	
	/**
	 * 修改个人信息
	 */
	public final static String updateUserInfo = "http://" + baseUrl + "/api/User/UpdateUserInfo";
	
	/**
	 * 修改密码
	 */
	public final static String changePassword = "http://" + baseUrl + "/api/User/ChangePassword";
	
	/**
	 * 获取皮肤类型小类
	 */
	public final static String getSkinList = "http://" + baseUrl + "/api/User/GetSkinList";
	
	/**
	 * 获取敏感类型小类
	 */
	public final static String getSensitiveList = "http://" + baseUrl + "/api/User/GetSensitiveList";

	/**
	 * 上传图片接口
	 */
	public final static String uploadImageByte = "http://" + baseUrl + "/api/User/UploadImageByte";

	/**
	 * 上传图片信息接口
	 */
	public final static String uploadImage = "http://" + baseUrl + "/api/User/UploadImage";

	/**
	 * 上传检测数据
	 */
	public final static String uploadDetection = "http://" + baseUrl + "/api/User/UploadDetection";

	/**
	 * 下载检测数据
	 */
	public final static String getDetection = "http://" + baseUrl + "/api/User/GetDetection";

	/**
	 * 上传日志接口
	 */
	public final static String uploadNotice = "http://" + baseUrl + "/api/User/UploadNotice";
}
