package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class MRWelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final View view = View.inflate(this, R.layout.mr_activity_welcome, null);
		//LinearLayout wellcome = (LinearLayout) view.findViewById(R.id.app_start_view);
		setContentView(R.layout.mr_activity_welcome);
		
		
		//渐变展示启动屏
		AlphaAnimation aa = new AlphaAnimation(0.3f,1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationStart(Animation animation) {}
			
		});
		/*
		new Handler(getMainLooper()).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				MRWelcomeActivity.this.startActivity(new Intent(MRWelcomeActivity.this, MRMainActivity.class));
				
			}
		}, 2000);
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrwelcome, menu);
		return true;
	}
	
    /**
     * 跳转到...
     */
    private void redirectTo(){        
        Intent intent = new Intent(this, MRMainActivity.class);
        startActivity(intent);
        finish();
    }

}
