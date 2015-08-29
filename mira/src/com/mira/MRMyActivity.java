package com.mira;

import org.kymjs.kjframe.KJBitmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.model.User;
import com.service.BluetoothService;
import com.umeng.analytics.MobclickAgent;
import com.utils.Tools;
import com.utils.UpdateManager;

public class MRMyActivity extends Activity {
	private Long mExitTime = 0l;
	
	private ImageView ivUserface;
	
	private ImageButton iBtn_help;
	//个人资料
	private RelativeLayout rlUserInfo;
	//我的变化
	private RelativeLayout rlChanges;
	//历史记录
	private RelativeLayout rlHistory;
	//设置
	private RelativeLayout rlSetting;
	
	private TextView tv_nickName;

//	private TextView tv_sign;
	
//	private TextView tv_level;
	
	private TextView tv_grades;
	
	private TextView tv_check_update;
	
	/**
	 * 退出按钮
	 */
	private Button btnLogout;
	
	/**
	 * 当前版本
	 */
	private TextView tv_versionName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my);
		
		tv_nickName = (TextView) this.findViewById(R.id.my_activity_nickName);
//		tv_sign = (TextView) this.findViewById(R.id.my_activity_sign);
//		tv_level = (TextView) this.findViewById(R.id.my_activity_level);
		tv_grades = (TextView) this.findViewById(R.id.my_activity_grades);
		ivUserface = (ImageView) this.findViewById(R.id.my_activity_userface);
		
		tv_versionName = (TextView) this.findViewById(R.id.my_activity_tv_version_name);
		String versionName = Tools.getVerName(this);
		tv_versionName.setText("当前版本"+versionName);
		
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
				//个人资料
				Intent intent = new Intent(v.getContext(), MRUserInfoActivity.class);
				startActivity(intent);
					
//				if(AppContext.getInstance().isLogin()){
//				}else{
//					Intent intent = new Intent(v.getContext(), MRLoginActivity.class);
//					startActivity(intent);
//				}
			}
		});
		
		rlChanges = (RelativeLayout) this.findViewById(R.id.my_activity_rl_changes);
		rlChanges.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "my_changes_click");
				//我的变化
				Intent intent = new Intent(v.getContext(), MRMyChangesActivity.class);
				startActivity(intent);
			}
		});
		
		rlHistory = (RelativeLayout) this.findViewById(R.id.my_activity_rl_history);
		rlHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "my_history_click");
				Intent intent = new Intent(v.getContext(), MRPastRecordsActivity.class);
				startActivity(intent);
			}
		});
		
		rlSetting = (RelativeLayout) this.findViewById(R.id.my_activity_rl_check_update);
		rlSetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Intent intent = new Intent(v.getContext(), MRSetActivity.class);
//				startActivity(intent);
			}
		});
		
		//退出
		btnLogout = (Button) findViewById(R.id.my_activity_bt_logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AppContext.getInstance().cleanLoginInfo();
				//跳到登录页面
				Intent intent = new Intent(v.getContext(), MRLoginActivity.class);
	    		startActivity(intent);
	    		finish();
			}
		});
		
		tv_check_update = (TextView) this.findViewById(R.id.my_activity_tv_check_update);
		tv_check_update.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				UpdateManager updateManager = new UpdateManager(MRMyActivity.this);
				updateManager.checkUpdate();
				
//				if(Tools.isNetworkAvailable(MRMyActivity.this)){
//				}else{
//					Toast.makeText(getApplicationContext(), R.string.network_exception, Toast.LENGTH_SHORT).show();
//					return;
//				}
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
				Intent intent = new Intent(MRMyActivity.this, BluetoothService.class);
			    stopService(intent);
			    MobclickAgent.onKillProcess(MRMyActivity.this);
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
			new KJBitmap().displayWithLoadBitmap(ivUserface, user.getFace(), R.drawable.head_pic);
		} else {
			tv_nickName.setText("未登录");
		}
		
	}
	
}
