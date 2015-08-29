package com.mira;


import java.util.HashMap;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import com.common.BaiDuLocationModel;
import com.common.BaiduLocation;
import com.common.HandlerEvent;
import com.service.BluetoothService;
import com.umeng.analytics.MobclickAgent;
import com.utils.Tools;
import com.utils.UpdateManager;

public class MRMainActivity extends TabActivity {
	
	private Long mExitTime = 0l;
	
	private final static String TAG = "MRMainActivity";
	
	TabHost tabHost;
	ImageView ivIndex;
	//ImageView ivFind;
	ImageView ivTest;
	ImageView ivMy;
	LinearLayout llIndex;
	//LinearLayout llFind;
	LinearLayout llTest;
	LinearLayout llMy;
	
	//TextView tvIndex;
	//TextView tvFind;
	//TextView tvTest;
	//TextView tvMy;
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_main);
//		MRDataBase.Init(this);
		mHandler = new Handler();
		//检查更新
		//checkUpdate.start();
		
		BaiduLocation.getLocation(this, new HandlerEvent<BaiDuLocationModel>(){
			public void handleMessage(BaiDuLocationModel result) {
				String city = result.City;
			};
		});
		
		ivIndex=(ImageView)findViewById(R.id.iv_index);
		//ivFind=(ImageView)findViewById(R.id.iv_find);
		ivTest=(ImageView)findViewById(R.id.iv_test);
		ivMy=(ImageView)findViewById(R.id.iv_my);
		
		llIndex=(LinearLayout)findViewById(R.id.ll_index);
		//llFind=(LinearLayout)findViewById(R.id.ll_find);
		llTest=(LinearLayout)findViewById(R.id.ll_test);
		llMy=(LinearLayout)findViewById(R.id.ll_my);
		
		//tvIndex=(TextView)findViewById(R.id.tv_index);
		//tvFind=(TextView)findViewById(R.id.tv_find);
		//tvTest=(TextView)findViewById(R.id.tv_test);
		//tvMy=(TextView)findViewById(R.id.tv_my);
		View.OnClickListener ibClick=new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.ll_index:
					tabHost.setCurrentTab(0);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation_select);
					//ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo);
					//tvIndex.setTextColor(0xff63c5c8);
					//tvFind.setTextColor(0xff888888);
					//tvTest.setTextColor(0xff888888);
					//tvMy.setTextColor(0xff888888);
					break;
				case R.id.ll_test:
					tabHost.setCurrentTab(1);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation);
					//ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check_select);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo);
					
					//tvIndex.setTextColor(0xff888888);
					//tvFind.setTextColor(0xff888888);
					//tvTest.setTextColor(0xff63c5c8);
					//tvMy.setTextColor(0xff888888);
					break;
				/*
				case R.id.ll_find:
					tabHost.setCurrentTab(2);	
					ivIndex.setBackgroundResource(R.drawable.tab_infomation);
					ivFind.setBackgroundResource(R.drawable.tab_discover_select);
					ivTest.setBackgroundResource(R.drawable.tab_check);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo);
					tvIndex.setTextColor(0xff888888);
					tvFind.setTextColor(0xff63c5c8);
					tvTest.setTextColor(0xff888888);
					tvMy.setTextColor(0xff888888);
					break;
				*/
				case R.id.ll_my:
					tabHost.setCurrentTab(2);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation);
					//ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo_select);
					
					//tvIndex.setTextColor(0xff888888);
					//tvFind.setTextColor(0xff888888);
					//tvTest.setTextColor(0xff888888);
					//tvMy.setTextColor(0xff63c5c8);
					break;
				default:
					break;
				}
				
				
			}
		};
		llIndex.setOnClickListener(ibClick);
		//llFind.setOnClickListener(ibClick);
		llTest.setOnClickListener(ibClick);
		llMy.setOnClickListener(ibClick);
	
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("a").setIndicator("0").setContent(new Intent(MRMainActivity.this, MRIndexActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("b").setIndicator("1").setContent(new Intent(MRMainActivity.this, MRDetectionMenuActivity.class)));
		//tabHost.addTab(tabHost.newTabSpec("c").setIndicator("2").setContent(new Intent(MRMainActivity.this, MRFindActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("c").setIndicator("2").setContent(new Intent(MRMainActivity.this, MRMyActivity.class)));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				Log.d(TAG, arg0);
				if("a".equalsIgnoreCase(arg0)){
					
				}
			}
		});
		
		tabHost.setCurrentTab(0);
		ivIndex.setBackgroundResource(R.drawable.tab_infomation_select);
		//ivFind.setBackgroundResource(R.drawable.tab_discover);
		ivTest.setBackgroundResource(R.drawable.tab_check);
		ivMy.setBackgroundResource(R.drawable.tab_myinfo);
		
		//tvIndex.setTextColor(0xff63c5c8);
		//tvFind.setTextColor(0xff888888);
		//tvTest.setTextColor(0xff888888);
		//tvMy.setTextColor(0xff888888);
		//MRCommon.update(this);
	}
	@Override
	protected void onResume() {
		BaiduLocation.getLocation(this, new HandlerEvent<BaiDuLocationModel>(){
			public void handleMessage(BaiDuLocationModel result) {
				
			};
		});
//		TestModel model = BaiduLocation.getWeather(this);
//		if(model == null){
//			Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//		}else{
//			Toast.makeText(this, model.toString(), Toast.LENGTH_SHORT).show();
//		}
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrmain, menu);
		return true;
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
				Intent intent = new Intent(MRMainActivity.this, BluetoothService.class);
			    stopService(intent);
			    MobclickAgent.onKillProcess(MRMainActivity.this);
				int pid = android.os.Process.myTid();
	            android.os.Process.killProcess(pid);
			}
		return flag;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	 /* This Thread checks for Updates in the Background */
    private Thread checkUpdate = new Thread(){
        public void run() {
            try {
            	UpdateManager updateManager = new UpdateManager(MRMainActivity.this);
    			updateManager.checkUpdate();
//            	// 获取当前软件版本
//        		int versionCode = Tools.getVerCode(MRMainActivity.this);
//        		// 服务端软件版本
//        		int serviceCode = -1;
//        		HashMap<String,String> mHashMap = Tools.getServerVerInfo();
//        		if (null != mHashMap) {
//        			serviceCode = Integer.valueOf(mHashMap.get("version"));
//        		}
//
//                // 如果服务端版本大于当前版本
//                if (serviceCode > versionCode) {
//                    //执行更新
//                	mHandler.post(updateVersion);
//                }
            } catch (Exception e) {
            	Log.e(TAG, "Thread　checkUpdate " + e.getMessage());
            }
        }
    };

    //更新的次线程
//	private Runnable updateVersion = new Runnable(){
//		@Override
//		public void run() {
//			UpdateManager updateManager = new UpdateManager(MRMainActivity.this);
//			updateManager.checkUpdate();
//		}
//		
//	};

}
