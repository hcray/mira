package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MRMyActivity extends Activity {
	private ImageButton iBtn_help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my);
		
		iBtn_help = (ImageButton) this.findViewById(R.id.user_info_ibtn_help);
		iBtn_help.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {
 				//下一步
 				Intent intent = new Intent(v.getContext(), MRGradesRuleActivity.class);
 		    	startActivity(intent);
 			}
		});
		
	}
}
