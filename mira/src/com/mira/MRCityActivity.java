package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MRCityActivity extends Activity {
	private LinearLayout backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_city);
		
		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRCityActivity.this.finish();
			}
		});
	}
}
