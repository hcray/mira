package com.mira;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.base.BaseApplication;
import com.bean.ResultBean;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.model.User;
import com.utils.HttpKit;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author CYY
 *
 */
public class AppContext extends BaseApplication {
	private static AppContext instance;

	private String loginUid;

	private boolean login;
	
	private String appId;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		login = getLoginStatus();
		loginUid = "-1";
	}

	public String getLoginUid() {
		return loginUid;
	}

	public boolean isLogin() {
		return login;
	}

	/**
	 * 获得当前app运行的AppContext
	 * 
	 * @return
	 */
	public static AppContext getInstance() {
		return instance;
	}

	/**
	 * 获取App唯一标识
	 * 
	 * @return
	 */
	public String getAppId() {
		String uniqueId = "";
		if (appId != null && !appId.isEmpty()) {
			uniqueId = appId;
			
		} else {
			final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
			final String tmDevice, tmSerial, androidId;
			tmDevice = "" + tm.getDeviceId();
			tmSerial = "" + tm.getSimSerialNumber();
			androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
			uniqueId = deviceUuid.toString();
			appId = uniqueId;
		}
		return uniqueId;
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public void saveUserInfo(final User user) {
		this.loginUid = user.getAccount();
		this.login = true;

		SharedPreferences preferences = context().getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		
		editor.putString("user.nickName", user.getNickName());
		editor.putInt("user.sex", user.getSex());
		editor.putString("user.account", user.getAccount());
		editor.putString("user.birthday", user.getBirthday());
		editor.putString("user.userId", user.getUserId());
		editor.putString("user.face", user.getFace());
		
		editor.putBoolean("user.isLogin", login);
		editor.commit();

	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public void updateUserInfo(final User user) {
		SharedPreferences preferences = context().getSharedPreferences("user",
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("user.nickName", user.getNickName());
		editor.putInt("user.sex", user.getSex());
		editor.putString("user.birthday", user.getBirthday());
		editor.putString("user.face", user.getFace());
		
		editor.commit();
		User curUser = getLoginUser();
		Log.d("AppContext", getAppId());
		HttpKit.updateUserInfo(getAppId(), curUser.getUserId(), curUser.getNickName(), curUser.getSex(), curUser.getBirthday(), "", "", handler);
		
	}

	/**
	 * 获得登录用户的信息
	 * 
	 * @return
	 */
	public User getLoginUser() {
		User user = new User();
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		String nickName = preferences.getString("user.nickName", user.getNickName());
		int sex = preferences.getInt("user.sex", user.getSex());
		String account = preferences.getString("user.account", user.getAccount());
		String birthday = preferences.getString("user.birthday", user.getBirthday());
		String userId = preferences.getString("user.userId", user.getUserId());
		String face = preferences.getString("user.face", user.getUserId());

		user.setAccount(account);
		user.setNickName(nickName);
		user.setSex(sex);
		user.setBirthday(birthday);
		user.setUserId(userId);
		user.setFace(face);
		return user;
	}
	
	/**
	 * 当前登录状态
	 * @return
	 */
	private boolean getLoginStatus(){
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		boolean isLogin = preferences.getBoolean("user.isLogin", false);
		return isLogin;
	}
	
	/**
	 * 今天推荐的DIY面膜
	 * @return
	 */
	public int getRecommendNum(){
		int retNum = 1;
		SharedPreferences preferences = context().getSharedPreferences("recommend", Context.MODE_PRIVATE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		String curDate = format.format(new Date());
		//上次的日期
		String date = preferences.getString("recommendDate", "");
		//推荐的编号
		int num = preferences.getInt("recommendNum", 1);
		//同一天
		if(curDate.equalsIgnoreCase(date)){
			retNum = num;
		} else {
			//不是一天、重新生成
			retNum = new Random().nextInt(10)+1;
			//保存每天生成的日期以及编号
			Editor editor = preferences.edit();
			editor.putString("recommendDate", curDate);
			editor.putInt("recommendNum", retNum);
			editor.commit();
		}
		
		return retNum;
	}

	/**
	 * 清除登录信息
	 */
	public void cleanLoginInfo() {
		this.loginUid = "0";
		this.login = false;
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
	
	/**
	 * 修改用户后的回调
	 */
	private final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d("AppContext", "handler: " + response.toString());
			Gson gson = new Gson();
			ResultBean retBean = gson.fromJson(response.toString(), ResultBean.class);
			//成功
			if(retBean.getResultCode() == 0){
				
			}
		}
	};

}
