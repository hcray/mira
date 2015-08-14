package com.utils;

import java.io.IOException;

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
	public static ResultBean createToken(String UUID, String mobileType,
			String token) {
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.createToken);
		httpPost.addHeader("UUID", UUID);
		httpPost.addHeader("MobileType", mobileType);
		httpPost.addHeader("Token", token);
		
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
	 * 用户登录
	 * @param UUID 是	设备号
	 * @param loginName 是	登录名
	 * @param type 是	登录类型 1：手机号登录 2：个人帐号登录
	 * @param password 是	登录密码，Type=1:动态验证码 Type= 2：账户密码
	 * @return 登录结果
	 */
	public static LoginRet userLogin(String UUID, String loginName,
			String type, String password) {
		LoginRet rb = new LoginRet();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.userLogin);
		httpPost.addHeader("UUID", UUID);
		httpPost.addHeader("LoginName", loginName);
		httpPost.addHeader("Type", type);
		httpPost.addHeader("Password", password);
		
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
	 * 获取验证码
	 * @param UUID 是	设备号
	 * @param mobile 是	手机号码
	 * @return 结果
	 */
	public static ResultBean getVerificationCode(String UUID, String mobile) {
		ResultBean rb = new ResultBean();
		HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(MiraConstants.getVerificationCode);
		httpPost.addHeader("UUID", UUID);
		httpPost.addHeader("Mobile", mobile);
		
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
