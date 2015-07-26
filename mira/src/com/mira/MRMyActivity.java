package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.model.User;

public class MRMyActivity extends Activity {
	private Long mExitTime = 0l;
	
	private ImageButton iBtn_help;
	//个人资料
	private RelativeLayout rlUserInfo;
	//我的变化
	private RelativeLayout rlChanges;
	//历史记录
	private RelativeLayout rlHistory;
	
	private TextView tv_nickName;

//	private TextView tv_sign;
	
//	private TextView tv_level;
	
	private TextView tv_grades;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my);
		
		tv_nickName = (TextView) this.findViewById(R.id.my_activity_nickName);
//		tv_sign = (TextView) this.findViewById(R.id.my_activity_sign);
//		tv_level = (TextView) this.findViewById(R.id.my_activity_level);
		tv_grades = (TextView) this.findViewById(R.id.my_activity_grades);
		
		iBtn_help = (ImageButton) this.findViewById(R.id.my_activity_ibtn_help);
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
				if(AppContext.getInstance().isLogin()){
					//个人资料
					Intent intent = new Intent(v.getContext(), MRUserInfoActivity.class);
					startActivity(intent);
					
				}else{
					Intent intent = new Intent(v.getContext(), MRLoginActivity.class);
					startActivity(intent);
				}
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
				Intent intent = new Intent(v.getContext(), MRHistoryActivity.class);
				startActivity(intent);
			}
		});
		
		initUI();
	}
	
	
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		initUI();
	}



	/**
	 * 监听返回--是否退出程序
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, R.string.back_exit_tips, 2000).show();
				mExitTime = System.currentTimeMillis();
			} else {
				int pid = android.os.Process.myTid();
	            android.os.Process.killProcess(pid);
			}
		return flag;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 初始化界面
	 */
	private void initUI(){
		// 判断是否登录
		if (AppContext.getInstance().isLogin()) {
			User user = AppContext.getInstance().getLoginUser();
			tv_nickName.setText(user.getNickName());
//			tv_sign.setText(user.getSign());
//			tv_level.setText(user.getLevel());
			tv_grades.setText(user.getGrades());
		} else {
			tv_nickName.setText("未登录");
		}
		
	}
	
}
