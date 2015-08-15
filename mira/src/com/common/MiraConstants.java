package com.common;

public class MiraConstants {
	
	/**
	 * 编码
	 */
	public final static  String charset = "UTF-8"; 
	
	/**
	 * 服务端地址
	 */
	public final static String baseUrl = "http://";
	
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
}