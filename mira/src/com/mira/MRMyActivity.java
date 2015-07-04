package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class MRMyActivity extends Activity {
	
	private ImageButton iBtn_help;
	//个人资料
	private RelativeLayout rlUserInfo;
	//我的变化
	private RelativeLayout rlChanges;
	//历史记录
	private RelativeLayout rlHistory;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my);
		
		iBtn_help = (ImageButton) this.findViewById(R.id.user_info_ibtn_help);
		iBtn_help.setOnClickListener(new View.OnClickListener() {
 			public void onClick(View v) {
 				//积分帮助
 				Intent intent = new Intent(v.getContext(), MRGradesRuleActivity.class);
 		    	startActivity(intent);
 			}
		});
		
		rlUserInfo = (RelativeLayout) this.findViewById(R.id.my_activity_rl_user_info);
		rlUserInfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//个人资料
				Intent intent = new Intent(v.getContext(), MRUserInfoActivity.class);
				startActivity(intent);
			}
		});
		
		rlChanges = (RelativeLayout) this.findViewById(R.id.my_activity_rl_changes);
		rlChanges.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//我的变化
				Intent intent = new Intent(v.getContext(), MRMyChangesActivity.class);
				startActivity(intent);
			}
		});
		
		rlHistory = (RelativeLayout) this.findViewById(R.id.my_activity_rl_history);
		rlHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//历史记录
				Intent intent = new Intent(v.getContext(), MRHistoryActivity.class);
				startActivity(intent);
			}
		});
		
	}
}
