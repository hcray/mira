package com.utils;

import java.io.File;
import java.io.FileNotFoundException;

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
	 * @param UserId String 是	用户编号
	 * @param Position int 是	1：额头 2：脸颊 3：鼻子 4：下巴
	 * @param Humidity Decimal	是	湿度
	 * @param Temperature Decimal	是	温度
	 * @param Water Decimal	是	水分
	 * @param Score Decimal	是	评分
	 * @param Comment String	是	备注
	 * @return
	 */
	public static void uploadDetection(String UUID, String UserId,
			int Position, String Humidity, String Temperature, String Water,
			String Score, String Comment, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UserId", UserId);
		params.add("Position", String.valueOf(Position));
		params.add("Humidity", Humidity);
		params.add("Temperature", Temperature);
		params.add("Water", Water);
		params.add("Score", Score);
		params.add("Comment", Comment);
		client.post(MiraConstants.uploadDetection, params, handler);
	}
	
	public static void uploadImageByte(File image, AsyncHttpResponseHandler handler) throws FileNotFoundException {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
//		params.put("UUID", UUID);
		params.put("image", image);
		client.post(MiraConstants.uploadImageByte, params, handler);
	}
	
	
	public static void uploadImage(String UUID, String UserId,
			int Type, AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UserId", UserId);
		params.add("Type", String.valueOf(Type));
		client.post(MiraConstants.uploadImage, params, handler);
	}
	
	
	/**
	 * 上传头像
	 * @param image
	 * @param UUID  UUID
	 * @param UserId UserId
	 * @param handler handler
	 * @throws FileNotFoundException
	 */
	public static void uploadHeadPic(File image, String UUID, String UserId,
			AsyncHttpResponseHandler handler) throws FileNotFoundException {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("UUID", UUID);
		params.put("UserId", UserId);
		params.put("image", image);
		client.post(MiraConstants.uploadHead, params, handler);
	}
	
	/**
	 * 版本更新接口
	 * @param UUID String	是	设备号
	 * @param UserId String	是	用户编号
	 * @param Type Int	是	安卓：1 IOS：2 塞班：3
	 * @param Id Int	是	当前的版本编号
	 * @param handler
	 */
	public static void versionDetection(String UUID, String UserId, int Type, int Id,
			AsyncHttpResponseHandler handler) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("UserId", UserId);
		params.add("Type", String.valueOf(Type));
		params.add("Id", String.valueOf(Id));
		client.post(MiraConstants.versionDetection, params, handler);
	}
	
	
	/**
	 * 获取城市的天气
	 * @param cityName 城市名称
	 * @param handler 
	 */
	public static void getWeather(String cityName, AsyncHttpResponseHandler handler) {
		String url = "http://api.map.baidu.com/telematics/v3/weather?location="+ cityName+"&output=json&;ak=C849c6992c5232a86e0f6e4426e7ce29";
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, handler);
	}
}
