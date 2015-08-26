package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.MiraConstants;
import com.service.BluetoothService;
import com.umeng.analytics.MobclickAgent;

public class MRDetectionMenuActivity extends Activity{
	
	private static String tag = "MRDetectionMenuActivity";

	private TextView tvHead;
	
	private TextView tvFace;
	
	private TextView tvNose;
	
	private TextView tvChin;
	
	//private TextView menuSelected;
	
	private Long mExitTime = 0l;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_detection_menu);
		Log.d(tag, "setContentView()");
		
		tvHead = (TextView) this.findViewById(R.id.detection_menu_activity_head);
		tvHead.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "head_test_click");
				//menuSelected.setText(getString(R.string.detection_menu_activity_head_value));
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				//1：额头 2：脸颊 3：鼻子 4：下巴
				intent.putExtra(MiraConstants.PART, MiraConstants.PART_HEAD);
				startActivity(intent);
			}
		});
		
		tvFace = (TextView) this.findViewById(R.id.detection_menu_activity_face);
		tvFace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "face_test_click");
				//menuSelected.setText(getString(R.string.detection_menu_activity_face_value));
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				intent.putExtra(MiraConstants.PART, MiraConstants.PART_FACE);
				startActivity(intent);
			}
		});
		
		tvNose = (TextView) this.findViewById(R.id.detection_menu_activity_nose);
		tvNose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "nose_test_click");
				//menuSelected.setText(getString(R.string.detection_menu_activity_nose_value));
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				intent.putExtra(MiraConstants.PART, MiraConstants.PART_NOSE);
				startActivity(intent);
			}
		});
		
		tvChin = (TextView) this.findViewById(R.id.detection_menu_activity_chin);
		tvChin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "chin_test_click");
				//menuSelected.setText(getString(R.string.detection_menu_activity_chin_value));
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				intent.putExtra(MiraConstants.PART, MiraConstants.PART_CHIN);
				startActivity(intent);
			}
		});
		
		//menuSelected = (TextView) this.findViewById(R.id.detection_menu_activity_menu_selected);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
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
				Intent intent = new Intent(MRDetectionMenuActivity.this, BluetoothService.class);
			    stopService(intent);
			    MobclickAgent.onKillProcess(MRDetectionMenuActivity.this);
				int pid = android.os.Process.myTid();
	            android.os.Process.killProcess(pid);
			}
		return flag;
		}
		return super.onKeyDown(keyCode, event);
	}

}
