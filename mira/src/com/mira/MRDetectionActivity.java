package com.mira;

import java.util.ArrayList;
import java.util.List;

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

import com.model.TestModel;
import com.view.LineCharView;

public class MRDetectionActivity extends Activity {
	private LinearLayout backbtn;

	private final static String tag = "MRDetectionActivity";

	BLEReceiver receiver;
	// 测试数据集合
	ArrayList<TestModel> dataList;
	// 是否在测试
	boolean isTest;

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

		LineCharView lcv = (LineCharView) findViewById(R.id.detection_activity_lcv);
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
	protected void onResume() {
		super.onResume();
		receiver = new BLEReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.mira.action.BLUETOOTH_DATA");
		MRDetectionActivity.this.registerReceiver(receiver, filter);
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
		int wenDu = 0;
		int shiDu = 0;
		int shuiFen = 0;
		//int ziWaiXian = 0;
		for (int i = 0; i < dataList.size(); i++) {
			wenDu += dataList.get(i).wenDu;
			shiDu += dataList.get(i).shiDu;
			shuiFen += dataList.get(i).shuiFen;
			//ziWaiXian += dataList.get(i).ziWaiXian;
		}
		wenDu /= dataList.size();
		shiDu /= dataList.size();
		shuiFen /= dataList.size();
		//ziWaiXian /= dataList.size();

		tvTem.setText(wenDu);
		tvWet.setText(shiDu);
		tvWater.setText(shuiFen);
	}

	// 显示提示信息
	public void showToast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}
}
