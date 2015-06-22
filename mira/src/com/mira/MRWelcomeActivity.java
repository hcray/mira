package com.mira;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MRWelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_welcome);
		new Handler(getMainLooper()).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				MRWelcomeActivity.this.startActivity(new Intent(MRWelcomeActivity.this, MRMainActivity.class));
				
			}
		}, 2000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrwelcome, menu);
		return true;
	}

}
