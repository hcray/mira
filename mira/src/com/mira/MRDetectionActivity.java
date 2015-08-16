package com.mira;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.ResultBean;
import com.bll.MRTestBLL;
import com.common.MiraConstants;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.model.TestModel;
import com.model.User;
import com.utils.HttpKit;
import com.view.LineCharView;
import com.view.RoundProgressBar;

public class MRDetectionActivity extends Activity {
	private LinearLayout backbtn;

	private final static String tag = "MRDetectionActivity";

	BLEReceiver receiver;
	// 测试数据集合
	ArrayList<TestModel> dataList;
	// 是否在测试
	boolean isTest;

	/**
	 * 当前检测的部位
	 * 1：额头 2：脸颊 3：鼻子 4：下巴
	 */
	private int part;

	/**
	 * 温度
	 */
	private TextView tvTem;

	/**
	 * 湿度
	 */
	private TextView tvWet;

	/**
	 * 水分
	 */
	private TextView tvWater;

	/**
	 * 综合得分
	 */
	private RoundProgressBar rpb;
	
	/**
	 * 标题
	 */
	private TextView tvTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_detection);
		tvTem = (TextView) this
				.findViewById(R.id.detection_activity_tv_tem_value);

		tvWet = (TextView) this
				.findViewById(R.id.detection_activity_tv_wet_value);

		tvWater = (TextView) this
				.findViewById(R.id.detection_activity_tv_water_value);

		rpb = (RoundProgressBar) this.findViewById(R.id.detection_activity_rpb);

		LineCharView lcv = (LineCharView) findViewById(R.id.detection_activity_lcv);
		
		tvTitle = (TextView) findViewById(R.id.detection_activity_tv_title);

		Intent intent = getIntent();// getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		//当前检测的部位
		part = bundle.getInt(MiraConstants.PART);
		Log.d(tag, "part: " + part);
		
		if (MiraConstants.PART_HEAD == part) {
			tvTitle.setText(getString(R.string.detection_activity_title_head));
			
		} else if (MiraConstants.PART_FACE == part) {
			tvTitle.setText(getString(R.string.detection_activity_title_face));
			
		} else if (MiraConstants.PART_NOSE == part) {
			tvTitle.setText(getString(R.string.detection_activity_title_nose));
			
		} else if (MiraConstants.PART_CHIN == part) {
			tvTitle.setText(getString(R.string.detection_activity_title_chin));
		}

		List<String> x_coords = new ArrayList<String>();
		x_coords.add("1");
		x_coords.add("2");
		x_coords.add("3");
		x_coords.add("4");
		x_coords.add("5");
		x_coords.add("6");
		x_coords.add("7");

		List<Integer> x_coord_values = new ArrayList<Integer>();
		x_coord_values.add(0);
		x_coord_values.add(40);
		x_coord_values.add(28);
		x_coord_values.add(52);
		x_coord_values.add(90);
		x_coord_values.add(72);
		x_coord_values.add(65);
		lcv.setBgColor(Color.WHITE);
		lcv.setValue(x_coords, x_coord_values);

		backbtn = (LinearLayout) this
				.findViewById(R.id.detection_activity_backbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRDetectionActivity.this.finish();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		receiver = new BLEReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.mira.action.BLUETOOTH_DATA");
		MRDetectionActivity.this.registerReceiver(receiver, filter);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (receiver != null) {
			unregisterReceiverSafe(receiver);
		}
	}

	/*
	 * @Override protected void onResume() { super.onResume(); receiver = new
	 * BLEReceiver(); IntentFilter filter = new IntentFilter();
	 * filter.addAction("com.mira.action.BLUETOOTH_DATA");
	 * MRDetectionActivity.this.registerReceiver(receiver, filter); }
	 * 
	 * @Override protected void onPause() { super.onPause(); if (receiver !=
	 * null) { unregisterReceiverSafe(receiver); } }
	 */

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			unregisterReceiverSafe(receiver);
		}
	}

	/**
	 * 接受蓝牙传递的数据
	 * 
	 * @author CYY
	 *
	 */
	public class BLEReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("com.mira.action.BLUETOOTH_DATA")) {
				Bundle bundle = intent.getExtras();
				short wenDu = bundle.getShort("wenDu", (short) 0.0);
				short shiDu = bundle.getShort("shiDu", (short) 0.0);
				short shuiFen = bundle.getShort("shuiFen", (short) 0.0);
				short ziWaiXian = bundle.getShort("ziWaiXian", (short) 0.0);
				Log.d(tag, "wenDu: " + wenDu + " shiDu: " + shiDu
						+ " shuiFen: " + shuiFen + " ziWaiXian: " + ziWaiXian);
				TestModel model = new TestModel();
				model.wenDu = wenDu;
				model.shiDu = shiDu;
				model.shuiFen = shuiFen;
				model.ziWaiXian = ziWaiXian;

				if (shuiFen > 5 && !isTest) {
					isTest = true;
					dataList = new ArrayList<TestModel>();
					showToast("检测中，请勿放开...");
					resetUI();
				} else if (shuiFen < 5 && isTest && dataList.size() < 5) {
					showToast("测试未完成，请重试...");
					isTest = false;
				} else if (shuiFen > 5) {
					dataList.add(model);
					if (dataList.size() == 5) {
						updateUI();
					}
				} else if (shuiFen < 5) {
					isTest = false;
				}

				// showToast("wenDu: " + wenDu + " shiDu: " + shiDu +
				// " shuiFen: " + shuiFen + " ziWaixian: " + ziWaixian);
			}
		}
	}

	/**
	 * 更新页面数据
	 */
	private void updateUI() {
		short wenDu = 0;
		short shiDu = 0;
		short shuiFen = 0;
		// int ziWaiXian = 0;
		for (int i = 0; i < dataList.size(); i++) {
			wenDu += dataList.get(i).wenDu;
			shiDu += dataList.get(i).shiDu;
			shuiFen += dataList.get(i).shuiFen;
			// ziWaiXian += dataList.get(i).ziWaiXian;
		}
		wenDu /= dataList.size();
		shiDu /= dataList.size();
		shuiFen /= dataList.size();
		// ziWaiXian /= dataList.size();

		// 页面显示
		tvTem.setText(wenDu / 100 + "℃");
		tvWet.setText(shiDu + "%");
		tvWater.setText(shuiFen + "%");
		rpb.setProgress(shuiFen);
		TestModel testModel = new TestModel();
		
		testModel.time=System.currentTimeMillis();
		testModel.wenDu = wenDu;
		testModel.shiDu = shiDu;
		testModel.shuiFen = shuiFen;
		testModel.type = part;
		
		//保存数据库
		MRTestBLL.addTestModel(testModel, MRDetectionActivity.this);
		//上传服务器
		//TODO
		User user = AppContext.getInstance().getLoginUser();
		String UUID = AppContext.getInstance().getAppId();
		String userId = user.getUserId();
		Log.d(tag, "userId: " + userId);
		HttpKit.uploadDetection(UUID, userId, part, String.valueOf(shiDu), String.valueOf(wenDu), String.valueOf(shuiFen), String.valueOf(shuiFen), String.valueOf(shuiFen), handler);
	}

	/**
	 * 重置页面
	 */
	private void resetUI() {
		int wenDu = 0;
		int shiDu = 0;
		int shuiFen = 0;
		// 页面显示
		tvTem.setText(wenDu / 100 + "℃");
		tvWet.setText(shiDu + "%");
		tvWater.setText(shuiFen + "%");
		rpb.setProgress(shuiFen);
	}

	// 显示提示信息
	public void showToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 安全取消注册
	 * 
	 * @param receiver
	 */
	private void unregisterReceiverSafe(BroadcastReceiver receiver) {
		try {
			MRDetectionActivity.this.unregisterReceiver(receiver);
		} catch (IllegalArgumentException e) {
			// ignore
		}
	}
	
	/**
	 * 修改用户后的回调
	 */
	private final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d(tag, "uploadDetection handler: " + response.toString());
			Gson gson = new Gson();
			ResultBean retBean = gson.fromJson(response.toString(), ResultBean.class);
			//成功
			if(retBean.getResultCode() == 0){
				
			}
		}
	};

}
