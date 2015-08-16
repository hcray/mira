package com.utils;

import com.common.MiraConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * http请求工具
 * @author CYY
 *
 */
public class HttpKit {

	/**
	 * 创建Token
	 * @param UUID 设备号	是	手机设备号
	 * @param mobileType 手机类型	是	1：安卓 2：苹果
	 * @param token 校验Token	是	由手机客户端UUID+约定字符进行MD5加密
	 * 获得的32位字符串
	 * 后面所有接口中返回的ResultCode=-2时需要调用此接口
	 * 
	 * @return 结果
	 */
	public static void createToken(String UUID, String mobileType,
			String token, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("MobileType", mobileType);
		params.add("Token", token);
		client.post(MiraConstants.createToken, params, handler);
		
	}
	
	/**
	 * 用户登录
	 * @param UUID 是	设备号
	 * @param loginName 是	登录名
	 * @param type 是	登录类型 1：手机号登录 2：个人帐号登录
	 * @param password 是	登录密码，Type=1:动态验证码 Type= 2：账户密码
	 * @return 登录结果
	 */
	public static void userLogin(String UUID, String loginName,
			String type, String password, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("LoginName", loginName);
		params.add("Type", type);
		params.add("Password", password);
		client.post(MiraConstants.userLogin, params, handler);
		
	}
	
	/**
	 * 获取验证码
	 * @param UUID 是	设备号
	 * @param mobile 是	手机号码
	 * @return 结果
	 */
	public static void getVerificationCode(String UUID, String mobile, AsyncHttpResponseHandler handler) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("Mobile", mobile);
		client.post(MiraConstants.getVerificationCode, params, handler);
		
	}
	
	/**
	 * 修改个人信息
	 * @param UUID 是	设备号
	 * @param userId 是	用户编号
	 * @param nickName 是	用户昵称
	 * @param sex 是	用户性别 1：男 2：女 3：保密
	 * @param birthday 是	生日
	 * @param skinId 是	皮肤类型编号
	 * @param sensitiveId 是	敏感类型编号
	 * @return
	 */
	public static void updateUserInfo(String UUID, String userId,
			String nickName, int sex, String birthday, String skinId,
			String sensitiveId, AsyncHttpResponseHandler handler) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UserId", userId);
		params.add("NickName", nickName);
		params.add("Sex", String.valueOf(sex));
		params.add("Birthday", birthday);
		params.add("SkinId", skinId);
		params.add("SensitiveId", sensitiveId);
		client.post(MiraConstants.updateUserInfo, params, handler);
		
	}
	
	/**
	 * @param UUID String	是	设备号
	 * @param userId String	是	用户编号
	 * @param oldPasswordHash String	是	原密码(MD532位加密)
	 * @param newPasswordHash String	是	新密码(MD532位加密)
	 * @return
	 */
	public static void changePassword(String UUID, String userId,
			String oldPasswordHash, String newPasswordHash, AsyncHttpResponseHandler handler) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UserId", userId);
		params.add("OldPasswordHash", oldPasswordHash);
		params.add("NewPasswordHash", newPasswordHash);
		client.post(MiraConstants.changePassword, params, handler);
	}
	
	/**
	 * 获取皮肤类型小类
	 * @param UUID
	 * @return
	 */
	public static void getSkinList(String UUID, AsyncHttpResponseHandler handler) {
		
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		client.post(MiraConstants.getSkinList, params, handler);
	}
	
	/**
	 * 获取敏感类型小类
	 * @param UUID
	 * @return
	 */
	public static void getSensitiveList(String UUID, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		client.post(MiraConstants.getSensitiveList, params, handler);
	}
	
	/**
	 * 上传检测数据
	 * @param UUID String 是	设备号
	 * @param UerId String 是	用户编号
	 * @param Position int 是	1：额头 2：脸颊 3：鼻子 4：下巴
	 * @param Humidity Decimal	是	湿度
	 * @param Temperature Decimal	是	温度
	 * @param Water Decimal	是	水分
	 * @param Score Decimal	是	评分
	 * @param Comment String	是	备注
	 * @return
	 */
	public static void uploadDetection(String UUID, String UerId,
			int Position, String Humidity, String Temperature, String Water,
			String Score, String Comment, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UerId", UerId);
		params.add("Position", String.valueOf(Position));
		params.add("Humidity", Humidity);
		params.add("Temperature", Temperature);
		params.add("Water", Water);
		params.add("Score", Score);
		params.add("Comment", Comment);
		client.post(MiraConstants.uploadDetection, params, handler);
	}
	
	
}
