package com.mira;

import com.common.MiraConstants;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MRRecommendActivity extends Activity {
	
	private LinearLayout backbtn;
	
	private TextView tv_name;
	
	private TextView tv_type;
	
	private TextView tv_method;
	
	private TextView tv_explain;
	
	private ImageView iv_pic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_recommend);
		
		tv_name = (TextView) this.findViewById(R.id.recommend_activity_tv_name);
		tv_type = (TextView) this.findViewById(R.id.recommend_activity_tv_type_content);
		tv_method = (TextView) this.findViewById(R.id.recommend_activity_tv_method_content);
		tv_explain = (TextView) this.findViewById(R.id.recommend_activity_tv_explain_content);
		iv_pic = (ImageView) this.findViewById(R.id.recommend_activity_iv_pic);
		
		Intent intent = getIntent();// getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		int recommendNum = bundle.getInt(MiraConstants.recommend);
		
		String name = "";
		String type = "";
		String method = "";
		String explain = "";
		Drawable pic = null;
		
		switch (recommendNum) {
		case 1:
			name = getString(R.string.recommend_activity_name1);
			type = getString(R.string.recommend_activity_type1);
			method = getString(R.string.recommend_activity_method1);
			explain = getString(R.string.recommend_activity_explain1);
			pic = getResources().getDrawable(R.drawable.recommend1);
			break;
		case 2:
			name = getString(R.string.recommend_activity_name2);
			type = getString(R.string.recommend_activity_type2);
			method = getString(R.string.recommend_activity_method2);
			explain = getString(R.string.recommend_activity_explain2);
			pic = getResources().getDrawable(R.drawable.recommend2);
			break;
		case 3:
			name = getString(R.string.recommend_activity_name3);
			type = getString(R.string.recommend_activity_type3);
			method = getString(R.string.recommend_activity_method3);
			explain = getString(R.string.recommend_activity_explain3);
			pic = getResources().getDrawable(R.drawable.recommend3);
			break;
		case 4:
			name = getString(R.string.recommend_activity_name4);
			type = getString(R.string.recommend_activity_type4);
			method = getString(R.string.recommend_activity_method4);
			explain = getString(R.string.recommend_activity_explain4);
			pic = getResources().getDrawable(R.drawable.recommend4);
			break;
		case 5:
			name = getString(R.string.recommend_activity_name5);
			type = getString(R.string.recommend_activity_type5);
			method = getString(R.string.recommend_activity_method5);
			explain = getString(R.string.recommend_activity_explain5);
			pic = getResources().getDrawable(R.drawable.recommend5);
			break;
		case 6:
			name = getString(R.string.recommend_activity_name6);
			type = getString(R.string.recommend_activity_type6);
			method = getString(R.string.recommend_activity_method6);
			explain = getString(R.string.recommend_activity_explain6);
			pic = getResources().getDrawable(R.drawable.recommend6);
			break;
		case 7:
			name = getString(R.string.recommend_activity_name7);
			type = getString(R.string.recommend_activity_type7);
			method = getString(R.string.recommend_activity_method7);
			explain = getString(R.string.recommend_activity_explain7);
			pic = getResources().getDrawable(R.drawable.recommend7);
			break;
		case 8:
			name = getString(R.string.recommend_activity_name8);
			type = getString(R.string.recommend_activity_type8);
			method = getString(R.string.recommend_activity_method8);
			explain = getString(R.string.recommend_activity_explain8);
			pic = getResources().getDrawable(R.drawable.recommend8);
			break;
		case 9:
			name = getString(R.string.recommend_activity_name9);
			type = getString(R.string.recommend_activity_type9);
			method = getString(R.string.recommend_activity_method9);
			explain = getString(R.string.recommend_activity_explain9);
			pic = getResources().getDrawable(R.drawable.recommend9);
			break;
		case 10:
			name = getString(R.string.recommend_activity_name10);
			type = getString(R.string.recommend_activity_type10);
			method = getString(R.string.recommend_activity_method10);
			explain = getString(R.string.recommend_activity_explain10);
			pic = getResources().getDrawable(R.drawable.recommend10);
			break;

		default:
			break;
		}
		
		tv_name.setText(name);
		tv_method.setText(method);
		tv_type.setText(type);
		tv_explain.setText(explain);
		if(null != pic){
			iv_pic.setImageDrawable(pic);
		}
		
		backbtn = (LinearLayout) this.findViewById(R.id.recommendBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRRecommendActivity.this.finish();
			}
		});
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
}
