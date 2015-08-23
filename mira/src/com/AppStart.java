package com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import com.mira.MRGuideActivity;
import com.mira.MRWelcomeActivity;

/**
 * 应用启动界面
 * 
 */
public class AppStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		SharedPreferences preferences = getApplicationContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
		boolean firstLogin = preferences.getBoolean("login", true);
		if (firstLogin) {
			redirectToGuide();
		} else {
			redirectToWel();
		}
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 跳转到欢迎页面
     */
    private void redirectToWel() {
    	Intent intent = new Intent(this, MRWelcomeActivity.class);
		startActivity(intent);
        finish();
    }
    
    /**
     * 跳转到引导页
     */
    private void redirectToGuide() {
    	SharedPreferences preferences = getApplicationContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("login", false);
		editor.commit();
    	Intent intent = new Intent(this, MRGuideActivity.class);
    	startActivity(intent);
    	finish();
    }
}
