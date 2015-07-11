package com;

import java.util.UUID;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.base.BaseApplication;

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
}
