package com.mira;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MRFindActivity extends Activity {
	private Long mExitTime = 0l;
	
	int width;
	int height;
	ImageButton ibMask;
	LinearLayout llQuDou;
	LinearLayout llBuShui;
	LinearLayout llKangZhou;
	LinearLayout llMeiBai;
	LinearLayout llDanBan;
	LinearLayout llKongYou;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_find);
		ibMask=(ImageButton)findViewById(R.id.ib_mask);
		llQuDou=(LinearLayout)findViewById(R.id.ll_qudou);
		llBuShui=(LinearLayout)findViewById(R.id.ll_bushui);
		llKangZhou=(LinearLayout)findViewById(R.id.ll_kangzhou);
		llMeiBai=(LinearLayout)findViewById(R.id.ll_meibai);
		llDanBan=(LinearLayout)findViewById(R.id.ll_danban);
		llKongYou=(LinearLayout)findViewById(R.id.ll_kongyou);
		View.OnClickListener click=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url="http://apinet.miramask.com/app/NewsList/";
				switch(v.getId())
				{
					case R.id.ll_qudou :
						url+="1001";
						break;
					case R.id.ll_bushui :
						url+="1002";
						break;
					case R.id.ll_kangzhou :
						url+="1003";
						break;
					case R.id.ll_meibai :
						url+="1005";
						break;
					case R.id.ll_danban :
						url+="1006";
						break;
					case R.id.ll_kongyou :
						url+="1007";
						break;
					default:break;
				}
				Intent intent=new Intent(MRFindActivity.this, MRWebViewActivity.class).putExtra("url", url);
				MRFindActivity.this.startActivity(intent);
			}
		};
		
		llQuDou.setOnClickListener(click);
		llBuShui.setOnClickListener(click);
		llKangZhou.setOnClickListener(click);
		llMeiBai.setOnClickListener(click);
		llDanBan.setOnClickListener(click);
		llKongYou.setOnClickListener(click);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)ibMask.getLayoutParams();
		lp.height=width*444/720;
		ibMask.setLayoutParams(lp);
		ibMask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MRFindActivity.this, MRWebViewActivity.class).putExtra("url", "http://apinet.miramask.com/app/Question?p=android");
				MRFindActivity.this.startActivity(intent);
			}
		});
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
