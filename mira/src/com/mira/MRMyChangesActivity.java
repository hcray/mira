package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MRMyChangesActivity extends Activity {

	private LinearLayout backbtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my_changes);
		
		backbtn = (LinearLayout) this.findViewById(R.id.myChangesBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRMyChangesActivity.this.finish();
			}
		});
	}
}
