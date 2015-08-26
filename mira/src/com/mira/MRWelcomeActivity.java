package com.mira;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class MRWelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// final View view = View.inflate(this, R.layout.mr_activity_welcome,
		// null);
		// LinearLayout wellcome = (LinearLayout)
		// view.findViewById(R.id.app_start_view);
		setContentView(R.layout.mr_activity_welcome);

		// 渐变展示启动屏
		/*
		 * AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		 * aa.setDuration(2000); view.startAnimation(aa);
		 * aa.setAnimationListener(new AnimationListener() {
		 * 
		 * @Override public void onAnimationEnd(Animation arg0) { redirectTo();
		 * }
		 * 
		 * @Override public void onAnimationRepeat(Animation animation) {}
		 * 
		 * @Override public void onAnimationStart(Animation animation) {}
		 * 
		 * });
		 */
		new Handler(getMainLooper()).postDelayed(new Runnable() {

			@Override
			public void run() {
				// MRWelcomeActivity.this.startActivity(new
				// Intent(MRWelcomeActivity.this, MRMainActivity.class));
				redirectTo();
			}
		}, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrwelcome, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	/**
	 * 跳转到...
	 */
	private void redirectTo() {
		// 判断是否登录，如果已经登录，则跳转主页面，没有登录，跳转登录页面
		boolean isLogin = AppContext.getInstance().isLogin();
		if (isLogin) {
			Intent intent = new Intent(this, MRMainActivity.class);
			startActivity(intent);

		} else {
			Intent intent = new Intent(this, MRLoginActivity.class);
			startActivity(intent);

		}
		finish();
	}

}
