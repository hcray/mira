package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MRDetectionMenuActivity extends Activity{
	
	private static String tag = "MRDetectionMenuActivity";

	private TextView tvHead;
	
	private TextView tvFace;
	
	private TextView tvNose;
	
	private TextView tvChin;
	
	private TextView menuSelected;
	
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
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				startActivity(intent);
			}
		});
		
		tvFace = (TextView) this.findViewById(R.id.detection_menu_activity_face);
		tvFace.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				startActivity(intent);
			}
		});
		
		tvNose = (TextView) this.findViewById(R.id.detection_menu_activity_nose);
		tvNose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				startActivity(intent);
			}
		});
		
		tvChin = (TextView) this.findViewById(R.id.detection_menu_activity_chin);
		tvChin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MRDetectionActivity.class);
				startActivity(intent);
			}
		});
		
		menuSelected = (TextView) this.findViewById(R.id.detection_menu_activity_menu_selected);
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

}
