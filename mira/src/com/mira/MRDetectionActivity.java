package com.mira;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.umeng.analytics.MobclickAgent;
import com.utils.DateUtil;
import com.utils.HttpKit;
import com.utils.Tools;
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
	 * 当前检测的部位 1：额头 2：脸颊 3：鼻子 4：下巴
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

	/**
	 * 历史记录
	 */
	private LineCharView lcv;

	/**
	 * 历史记录天
	 */
	private TextView tvDay;

	/**
	 * 历史记录周
	 */
	private TextView tvWeek;

	private TextView tvPartTestRet;
	
	/**
	 * 默认显示天的历史记录
	 */
	private boolean daySelect;
	
	/**
	 * 当前部位
	 */
	private String curPart;
	
	/**
	 * 时间戳
	 */
	private long timestampWater = 0l;
	
	/**
	 * 时间戳
	 */
	private long timestamp = 0l;
	
	/**
	 * 随机生成的平均值
	 */
	private int average;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 上次的环境湿度
	 */
	private short lastShiDu;

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

		tvDay = (TextView) this.findViewById(R.id.detection_activity_tv_day);

		tvWeek = (TextView) this.findViewById(R.id.detection_activity_tv_week);

		rpb = (RoundProgressBar) this.findViewById(R.id.detection_activity_rpb);

		lcv = (LineCharView) findViewById(R.id.detection_activity_lcv);

		tvTitle = (TextView) findViewById(R.id.detection_activity_tv_title);
		
		tvPartTestRet = (TextView) findViewById(R.id.detection_activity_tv_part_result);

		Intent intent = getIntent();// getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		// 当前检测的部位
		part = bundle.getInt(MiraConstants.PART);
		Log.d(tag, "part: " + part);

		if (MiraConstants.PART_HEAD == part) {
			curPart = getString(R.string.detection_activity_title_head);

		} else if (MiraConstants.PART_FACE == part) {
			curPart = getString(R.string.detection_activity_title_face);

		} else if (MiraConstants.PART_NOSE == part) {
			curPart = getString(R.string.detection_activity_title_nose);

		} else if (MiraConstants.PART_CHIN == part) {
			curPart = getString(R.string.detection_activity_title_chin);
		}
		tvTitle.setText(curPart);
		tvPartTestRet.setText(getString(R.string.detection_activity_part_result_start)+curPart+getString(R.string.detection_activity_part_result_end));
		
		// List<String> x_coords = new ArrayList<String>();
		// x_coords.add("1");
		// x_coords.add("2");
		// x_coords.add("3");
		// x_coords.add("4");
		// x_coords.add("5");
		// x_coords.add("6");
		// x_coords.add("7");
		//
		// List<Integer> x_coord_values = new ArrayList<Integer>();
		// x_coord_values.add(0);
		// x_coord_values.add(40);
		// x_coord_values.add(28);
		// x_coord_values.add(52);
		// x_coord_values.add(90);
		// x_coord_values.add(72);
		// x_coord_values.add(65);
		// lcv.setBgColor(Color.WHITE);
		// lcv.setValue(x_coords, x_coord_values);
		showDayHistory();
		daySelect = true;

		backbtn = (LinearLayout) this
				.findViewById(R.id.detection_activity_backbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRDetectionActivity.this.finish();
			}
		});

		tvDay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "test_day_view_click");
				// 修改选择样式
				tvDay.setBackgroundColor(Color.parseColor("#84dfd8"));
				tvDay.setTextColor(Color.parseColor("#ffffff"));

				tvWeek.setBackgroundColor(Color.parseColor("#e4f3f2"));
				tvWeek.setTextColor(Color.parseColor("#aaaaaa"));
				// 数据展示
				showDayHistory();
			}
		});

		tvWeek.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//埋点统计
				MobclickAgent.onEvent(v.getContext(), "test_week_view_click");
				// 修改选择样式
				tvWeek.setBackgroundColor(Color.parseColor("#84dfd8"));
				tvWeek.setTextColor(Color.parseColor("#ffffff"));

				tvDay.setBackgroundColor(Color.parseColor("#e4f3f2"));
				tvDay.setTextColor(Color.parseColor("#aaaaaa"));
				// 数据展示
				showWeekHistory();
			}
		});
		
		SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("Location", Context.MODE_PRIVATE);
		city = preferences.getString("City", "");
		//初始化综合分值
		rpb.setProgress(0);
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
		
		if (timestamp == 0l) {
			timestamp = System.currentTimeMillis();
			tvWet.setText(shiDu + "%");
			lastShiDu = shiDu;
		} else {
			//超过5分钟取检测数据
			if(System.currentTimeMillis() - timestamp > 5*60*1000){
				tvWet.setText(shiDu + "%");
				lastShiDu = shiDu;
				timestamp = System.currentTimeMillis();
			} else {
				//浮动超过0.1 ，取最新数据，在0.1之内，取上次的数据
				float ratio = (float)(shiDu-lastShiDu)/lastShiDu;
				if (ratio > 0.1 || ratio < -0.1) {
					tvWet.setText(shiDu + "%");
				} else {
					tvWet.setText(lastShiDu + "%");
				}
			}
		}
		
		tvWater.setText(shuiFen + "%");
		int score = Tools.getScore(shuiFen);
		rpb.setProgress(score);
		TestModel testModel = new TestModel();

		testModel.time = System.currentTimeMillis();
		testModel.wenDu = wenDu;
		testModel.shiDu = shiDu;
		testModel.shuiFen = shuiFen;
		testModel.type = part;
		testModel.score = score;

		// 保存数据库
		MRTestBLL.addTestModel(testModel, MRDetectionActivity.this);
		// 更新检测结果
		showDetectionRet(shuiFen);
		// 刷新历史数据
		if (daySelect) {
			showDayHistory();
		} else {
			showWeekHistory();
		}
		// 上传服务器
		User user = AppContext.getInstance().getLoginUser();
		String UUID = AppContext.getInstance().getAppId();
		String userId = user.getUserId();
		Log.d(tag, "userId: " + userId);
		HttpKit.uploadDetection(UUID, userId, part, String.valueOf(shiDu),
				String.valueOf(wenDu), String.valueOf(shuiFen),
				String.valueOf(score), String.valueOf(shuiFen), handler);
	}

	/**
	 * 重置页面
	 */
	private void resetUI() {
		int wenDu = 0;
		int shiDu = 0;
		int shuiFen = 0;
		int score = 0;
		// 页面显示
		tvTem.setText(wenDu / 100 + "℃");
		tvWet.setText(shiDu + "%");
		tvWater.setText(shuiFen + "%");
		rpb.setProgress(score);
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
			ResultBean retBean = gson.fromJson(response.toString(),
					ResultBean.class);
			// 成功
			if (retBean.getResultCode() == 0) {

			}
		}
	};

	/**
	 * 历史记录按照周显示，只显示每天最高的
	 */
	private void showWeekHistory() {
		daySelect = false;
		List<String> x_coords = new ArrayList<String>();
		List<Integer> x_coord_values = new ArrayList<Integer>();
		
		int dayOfWeek = DateUtil.getTodayOfWeek();
		int j = dayOfWeek - 1;
		for (int i = 1; i <= dayOfWeek; i++) {
			x_coords.add(Tools.getWeekDayName(i));
			long startTime = DateUtil.getTimesMorning(-j);
			long endTime = DateUtil.getTimesNight(-j);
			j--;
			int value = MRTestBLL.getTestMax(part, startTime, endTime,MRDetectionActivity.this);
			x_coord_values.add(value);
		}

//		for (int i = 0; i < 7; i++) {
//			x_coords.add(DateUtil.getMonthDay(-j));
//			long startTime = DateUtil.getTimesMorning(-j);
//			long endTime = DateUtil.getTimesNight(-j);
//			j--;
//			int value = MRTestBLL.getTestMax(part, startTime, endTime,MRDetectionActivity.this);
//			x_coord_values.add(value);
//		}
		lcv.setBgColor(Color.WHITE);
		lcv.setValue(x_coords, x_coord_values);
	}

	/**
	 * 历史记录按照天显示，最多显示最后七次
	 */
	private void showDayHistory() {
		daySelect = true;
		long time = DateUtil.getTimesMorning();
		List<TestModel> dayList = new ArrayList<TestModel>();

		List<TestModel> list = MRTestBLL.getTestList4Today(part,
				MRDetectionActivity.this, time);
		if (list.size() > 7) {
			dayList = list.subList(0, 7);
		} else {
			dayList = list;
		}

		if (dayList.isEmpty()) {

		} else {
			// 反序list
			Collections.reverse(dayList);
			int size = dayList.size();
			List<String> x_coords = new ArrayList<String>();
			List<Integer> x_coord_values = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				// 只显示七条记录
				long milliseconds = dayList.get(i).time;
				String xTime = DateUtil.getHourMinute(milliseconds);
				//x_coords.add(String.valueOf(i + 1));
				x_coords.add(xTime);
				x_coord_values.add((int) dayList.get(i).shuiFen);
			}
			lcv.setBgColor(Color.WHITE);
			lcv.setValue(x_coords, x_coord_values);
		}
	}
	
	/**
	 * 
	 */
	private void showDetectionRet(int myWater){
		//今天开始时间
		long startTime = DateUtil.getTimesMorning();
		//今天结束时间
		long endTime = DateUtil.getTimesNight();
		//系统平均水分
		//int average = 30 + (int) (Math.random() * 10);
		if (timestampWater == 0l) {
			average = new Random().nextInt(5) + 36;
			timestampWater = System.currentTimeMillis();
			
		} else {
			//超过5分钟重新设置随机数
			if(System.currentTimeMillis() - timestampWater > 5*60*1000){
				average = new Random().nextInt(5) + 36;
				timestampWater = System.currentTimeMillis();
			}
		}
		//我的平均水分
		//int myAvg = MRTestBLL.getTestAverage(part, startTime, endTime, MRDetectionActivity.this);
		StringBuilder text = new StringBuilder();
		//比值
		float avgRet = (float) (myWater - average) / average;
		DecimalFormat decimalFormat=new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		Log.d(tag, "myWater: " + myWater + " average: " + average + " avgRet: " + avgRet);
		if (avgRet < 0.05 && avgRet > -0.05) {
			if(avgRet > 0){
				text.append("小若若报告：今天").append(city).append("用户").append(curPart).append("平均水份值为").append(average).append("%，你高出平均水平").append(decimalFormat.format(avgRet*100)).append("%，继续努力哦！ ");
			}else{
				text.append("小若若报告：今天").append(city).append("用户").append(curPart).append("平均水份值为").append(average).append("%，你低于平均水平").append(decimalFormat.format(avgRet*100)).append("%，继续努力哦！ ");
			}
			
		} else if (avgRet <= -0.05) {
			text.append("小若若报告：今天").append(city).append("用户").append(curPart).append("平均水份值为").append(average).append("%，你低于平均水平").append(decimalFormat.format(avgRet*100)).append("%，赶紧去补水吧！ ");

		} else if (avgRet >= 0.05) {
			text.append("小若若报告：今天").append(city).append("用户").append(curPart).append("平均水份值为").append(average).append("%，你高出平均水平").append(decimalFormat.format(avgRet*100)).append("%，棒棒哒！ ");
		}
		
		tvPartTestRet.setText(text.toString());
	}

}
