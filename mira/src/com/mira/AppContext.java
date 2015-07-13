package com.mira;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.base.BaseApplication;
import com.model.User;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * @author CYY
 *
 */
public class AppContext extends BaseApplication {
	private static AppContext instance;

	private int loginUid;

	private boolean login;
	
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        login = false;
        loginUid = -1;
    }
    
    public int getLoginUid() {
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
        /*String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }*/
       String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
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
     * @param user
     */
    public void saveUserInfo(final User user) {
        this.loginUid = user.getAccount();
        this.login = true;
        
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor=preferences.edit();
		editor.putString("user.face", user.getFace());
		editor.putString("user.nickName", user.getNickName());
		editor.putString("user.sign", user.getSign());
		editor.putString("user.level", user.getLevel());
		editor.putString("user.grades", user.getGrades());
		editor.putString("user.height", user.getHeight());
		editor.putString("user.weight", user.getWeight());
		editor.putString("user.sex", user.getSex());
		editor.putString("user.account", String.valueOf(user.getAccount()));
		editor.putString("user.age", user.getAge());
		editor.commit();
      
    }
    

    /**
     * 更新用户信息
     * 
     * @param user
     */
    public void updateUserInfo(final User user) {
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("user.face", user.getFace());
		editor.putString("user.nickName", user.getNickName());
		editor.putString("user.sign", user.getSign());
		editor.putString("user.level", user.getLevel());
		editor.putString("user.grades", user.getGrades());
		editor.putString("user.height", user.getHeight());
		editor.putString("user.weight", user.getWeight());
		editor.putString("user.sex", user.getSex());
		editor.putString("user.account", String.valueOf(user.getAccount()));
		editor.putString("user.age", user.getAge());
		editor.commit();
    }

    /**
     * 获得登录用户的信息
     * 
     * @return
     */
    public User getLoginUser() {
        User user = new User();
		SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
		preferences.getString("user.face", user.getFace());
		preferences.getString("user.nickName", user.getNickName());
		preferences.getString("user.sign", user.getSign());
		preferences.getString("user.level", user.getLevel());
		preferences.getString("user.grades", user.getGrades());
		preferences.getString("user.height", user.getHeight());
		preferences.getString("user.weight", user.getWeight());
		preferences.getString("user.sex", user.getSex());
		preferences.getString("user.account", String.valueOf(user.getAccount()));
		preferences.getString("user.age", user.getAge());
		
        return user;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.loginUid = 0;
        this.login = false;
        SharedPreferences preferences = context().getSharedPreferences("user", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
