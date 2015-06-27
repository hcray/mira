package com.mira;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.common.BaiDuLocationModel;
import com.common.BaiduLocation;
import com.common.HandlerEvent;
import com.common.MRCommon;
import com.database.MRDataBase;

public class MRMainActivity extends TabActivity {
	TabHost tabHost;
	ImageView ivIndex;
	ImageView ivFind;
	ImageView ivTest;
	ImageView ivMy;
	LinearLayout llIndex;
	LinearLayout llFind;
	LinearLayout llTest;
	LinearLayout llMy;
	TextView tvIndex;
	TextView tvFind;
	TextView tvTest;
	TextView tvMy;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_main);
		MRDataBase.Init(this);
		
		ivIndex=(ImageView)findViewById(R.id.iv_index);
		ivFind=(ImageView)findViewById(R.id.iv_find);
		ivTest=(ImageView)findViewById(R.id.iv_test);
		ivMy=(ImageView)findViewById(R.id.iv_my);
		llIndex=(LinearLayout)findViewById(R.id.ll_index);
		llFind=(LinearLayout)findViewById(R.id.ll_find);
		llTest=(LinearLayout)findViewById(R.id.ll_test);
		llMy=(LinearLayout)findViewById(R.id.ll_my);
		tvIndex=(TextView)findViewById(R.id.tv_index);
		tvFind=(TextView)findViewById(R.id.tv_find);
		tvTest=(TextView)findViewById(R.id.tv_test);
		tvMy=(TextView)findViewById(R.id.tv_my);
		View.OnClickListener ibClick=new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (arg0.getId()) {
				case R.id.ll_index:
					tabHost.setCurrentTab(0);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation_select);
					ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo);
					tvIndex.setTextColor(0xff63c5c8);
					tvFind.setTextColor(0xff888888);
					tvTest.setTextColor(0xff888888);
					tvMy.setTextColor(0xff888888);
					break;
				case R.id.ll_test:
					tabHost.setCurrentTab(1);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation);
					ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check_select);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo);
					tvIndex.setTextColor(0xff888888);
					tvFind.setTextColor(0xff888888);
					tvTest.setTextColor(0xff63c5c8);
					tvMy.setTextColor(0xff888888);
					break;
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
				case R.id.ll_my:
					tabHost.setCurrentTab(3);
					ivIndex.setBackgroundResource(R.drawable.tab_infomation);
					ivFind.setBackgroundResource(R.drawable.tab_discover);
					ivTest.setBackgroundResource(R.drawable.tab_check);
					ivMy.setBackgroundResource(R.drawable.tab_myinfo_select);
					tvIndex.setTextColor(0xff888888);
					tvFind.setTextColor(0xff888888);
					tvTest.setTextColor(0xff888888);
					tvMy.setTextColor(0xff63c5c8);
					break;
				default:
					break;
				}
				
				
			}
		};
		llIndex.setOnClickListener(ibClick);
		llFind.setOnClickListener(ibClick);
		llTest.setOnClickListener(ibClick);
		llMy.setOnClickListener(ibClick);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("a").setIndicator("0").setContent(new Intent(MRMainActivity.this, MRIndexActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("b").setIndicator("1").setContent(new Intent(MRMainActivity.this, MRTestActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("c").setIndicator("2").setContent(new Intent(MRMainActivity.this, MRFindActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("d").setIndicator("3").setContent(new Intent(MRMainActivity.this, MRMyActivity.class)));
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		tabHost.setCurrentTab(0);
		ivIndex.setBackgroundResource(R.drawable.tab_infomation_select);
		ivFind.setBackgroundResource(R.drawable.tab_discover);
		ivTest.setBackgroundResource(R.drawable.tab_check);
		ivMy.setBackgroundResource(R.drawable.tab_myinfo);
		tvIndex.setTextColor(0xff63c5c8);
		tvFind.setTextColor(0xff888888);
		tvTest.setTextColor(0xff888888);
		tvMy.setTextColor(0xff888888);
		MRCommon.update(this);
	}
	@Override
	protected void onResume() {
		BaiduLocation.getLocation(this, new HandlerEvent<BaiDuLocationModel>(){
			public void handleMessage(BaiDuLocationModel result) {
				
			};
		});
		BaiduLocation.getWeather(this);
		super.onResume();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrmain, menu);
		return true;
	}

}
