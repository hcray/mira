package com.utils;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.bean.LoginRet;
import com.bean.ResultBean;
import com.common.MiraConstants;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
		
		
//		ResultBean rb = new ResultBean();
//		HttpClient httpClient = new DefaultHttpClient();
//		
//		HttpPost httpPost = new HttpPost(MiraConstants.createToken);
//		httpPost.addHeader("UUID", UUID);
//		httpPost.addHeader("MobileType", mobileType);
//		httpPost.addHeader("Token", token);
//		
//		try {
//			//执行请求
//			HttpResponse response = httpClient.execute(httpPost);
//			
//			HttpEntity entity = response.getEntity();
//			//返回值
//			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
//			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return rb;
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
			int type, String password, AsyncHttpResponseHandler handler) {
//		LoginRet rb = new LoginRet();
//		HttpClient httpClient = new DefaultHttpClient();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("UUID", UUID);
		params.add("LoginName", loginName);
		params.add("Type", String.valueOf(type));
		params.add("Password", password);
		client.post(MiraConstants.userLogin, params, handler);
		
//		HttpPost httpPost = new HttpPost(MiraConstants.userLogin);
//		httpPost.addHeader("UUID", UUID);
//		httpPost.addHeader("LoginName", loginName);
//		httpPost.addHeader("Type", String.valueOf(type));
//		httpPost.addHeader("Password", password);
//		
//		try {
//			//执行请求
//			HttpResponse response = httpClient.execute(httpPost);
//			
//			HttpEntity entity = response.getEntity();
//			//返回值
//			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
//			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return rb;
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
		
//		ResultBean rb = new ResultBean();
//		HttpClient httpClient = new DefaultHttpClient();
		
//		HttpPost httpPost = new HttpPost(MiraConstants.getVerificationCode);
//		httpPost.addHeader("UUID", UUID);
//		httpPost.addHeader("Mobile", mobile);
//		
//		try {
//			//执行请求
//			HttpResponse response = httpClient.execute(httpPost);
//			
//			HttpEntity entity = response.getEntity();
//			//返回值
//			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
//			
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return rb;
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
	public static ResultBean updateUserInfo(String UUID, String userId,
			String nickName, int sex, String birthday, String skinId,
			String sensitiveId) {
		
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.updateUserInfo);
		httpPost.addHeader("UUID", UUID);
		httpPost.addHeader("UserId", userId);
		httpPost.addHeader("NickName", nickName);
		httpPost.addHeader("Sex", String.valueOf(sex));
		httpPost.addHeader("Birthday", birthday);
		httpPost.addHeader("SkinId", skinId);
		httpPost.addHeader("SensitiveId", sensitiveId);
		
		try {
			//执行请求
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//返回值
			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * @param UUID String	是	设备号
	 * @param userId String	是	用户编号
	 * @param oldPasswordHash String	是	原密码(MD532位加密)
	 * @param newPasswordHash String	是	新密码(MD532位加密)
	 * @return
	 */
	public static ResultBean changePassword(String UUID, String userId,
			String oldPasswordHash, String newPasswordHash) {
		
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.changePassword);
		httpPost.addHeader("UUID", UUID);
		httpPost.addHeader("UserId", userId);
		httpPost.addHeader("OldPasswordHash", oldPasswordHash);
		httpPost.addHeader("NewPasswordHash", newPasswordHash);
		
		try {
			//执行请求
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//返回值
			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 获取皮肤类型小类
	 * @param UUID
	 * @return
	 */
	public static ResultBean getSkinList(String UUID) {
		
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.getSkinList);
		httpPost.addHeader("UUID", UUID);
		
		try {
			//执行请求
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//返回值
			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 获取敏感类型小类
	 * @param UUID
	 * @return
	 */
	public static ResultBean getSensitiveList(String UUID) {
		
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.getSensitiveList);
		httpPost.addHeader("UUID", UUID);
		
		try {
			//执行请求
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//返回值
			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
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
	public static ResultBean uploadDetection(String UUID, String UerId, int Position, String Humidity, String Temperature, String Water, String Score, String Comment) {
		
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.uploadDetection);
		httpPost.addHeader("UUID", UUID);
		
		try {
			//执行请求
			HttpResponse response = httpClient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			//返回值
			String retStr = EntityUtils.toString(entity, MiraConstants.charset);
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rb;
	}
	
	
}
