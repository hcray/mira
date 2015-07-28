package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MRSetActivity extends Activity {
	private LinearLayout backbtn;
	
	private Button btn_quit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_set);
		
		backbtn = (LinearLayout) this.findViewById(R.id.set_back_ll);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRSetActivity.this.finish();
			}
		});
		
		//退出按钮
		btn_quit = (Button) this.findViewById(R.id.set_activity_bt_quit);
		btn_quit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRSetActivity.this.finish();
			}
		});
	}
}
