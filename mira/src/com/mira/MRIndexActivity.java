package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageButton;

public class MRIndexActivity extends Activity {
	
	/**
	 * 相机 
	 */
	private ImageButton ibCamera;
	
	/**
	 * 日期选择
	 */
	private ImageButton ibCalender;
	
	/**
	 * 图片展示
	 */
	private ViewPager viewPager;
	
	/**
	 * 日期显示
	 */
	private EditText etSelectDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_index);
		
		ibCamera = (ImageButton) this.findViewById(R.id.ib_camera);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrindex, menu);
		return true;
	}

}
