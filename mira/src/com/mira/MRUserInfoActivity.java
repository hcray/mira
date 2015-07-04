package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MRUserInfoActivity extends Activity {

	private LinearLayout backbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_user_info);
		
		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRUserInfoActivity.this.finish();
			}
		});
	}
}
