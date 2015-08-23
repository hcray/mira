package com.mira;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cn.mira.datepicker.interfaces.OnDateSelected;
import cn.mira.datepicker.interfaces.OnMonthChange;
import cn.mira.datepicker.interfaces.OnYearChange;
import cn.mira.datepicker.views.DatePicker;

import com.bll.MRTestBLL;
import com.common.MiraConstants;
import com.model.TestModel;
import com.utils.DateUtil;
import com.view.LineCharView;

public class MRPastRecordsActivity extends Activity {
	
	private TextView tvYearTitle;

	private TextView tvMonthTitle;
	
	private LinearLayout backbtn;
	
	private Button btnViewSelect;
	
	private LinearLayout llDayView;

	private LinearLayout llWeekView;
	
	private DatePicker prDp;
	
	private final static String tag = "MRPastRecordsActivity";
	
	private String curSelectDate;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	/**
	 * 默认填视图
	 */
	private boolean dayViewFlag;
	
	private LinearLayout tabHead;
	
	private LinearLayout tabFace;
	
	private LinearLayout tabNose;
	
	private LinearLayout tabChin;
	
	private TextView tvAvgScore;
	
	private LinearLayout llProgress1;
	private RelativeLayout rlProgress1;
	private TextView tvProgressTime1;
	private TextView tvProgressScore1;
	
	private LinearLayout llProgress2;
	private RelativeLayout rlProgress2;
	private TextView tvProgressTime2;
	private TextView tvProgressScore2;
	
	private LinearLayout llProgress3;
	private RelativeLayout rlProgress3;
	private TextView tvProgressTime3;
	private TextView tvProgressScore3;
	
	private LinearLayout llProgress4;
	private RelativeLayout rlProgress4;
	private TextView tvProgressTime4;
	private TextView tvProgressScore4;
	
	private LinearLayout llProgress5;
	private RelativeLayout rlProgress5;
	private TextView tvProgressTime5;
	private TextView tvProgressScore5;

	/**
	 * 历史记录
	 */
	private LineCharView lcv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_past_records);
		dayViewFlag = true;
		tvYearTitle = (TextView) this.findViewById(R.id.pastRecord_activity_tv_year_title);

		tvMonthTitle = (TextView) this.findViewById(R.id.pastRecord_activity_tv_month_title);
		
		tvAvgScore = (TextView) this.findViewById(R.id.past_record_activity_avg_score);
		
		llProgress1 = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_progress1);
		rlProgress1 = (RelativeLayout) this.findViewById(R.id.past_record_activity_rl_progress1);
		tvProgressTime1 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_time1);
		tvProgressScore1 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_score1);
		
		llProgress2 = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_progress2);
		rlProgress2 = (RelativeLayout) this.findViewById(R.id.past_record_activity_rl_progress2);
		tvProgressTime2 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_time2);
		tvProgressScore2 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_score2);
	
		llProgress3 = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_progress3);
		rlProgress3 = (RelativeLayout) this.findViewById(R.id.past_record_activity_rl_progress3);
		tvProgressTime3 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_time3);
		tvProgressScore3 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_score3);
		
		llProgress4 = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_progress4);
		rlProgress4 = (RelativeLayout) this.findViewById(R.id.past_record_activity_rl_progress4);
		tvProgressTime4 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_time4);
		tvProgressScore4 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_score4);
		
		llProgress5 = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_progress5);
		rlProgress5 = (RelativeLayout) this.findViewById(R.id.past_record_activity_rl_progress5);
		tvProgressTime5 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_time5);
		tvProgressScore5 = (TextView) this.findViewById(R.id.past_record_activity_tv_progress_score5);
		
		lcv = (LineCharView) findViewById(R.id.past_records_activity_lcv);
		
		llDayView = (LinearLayout) this.findViewById(R.id.past_records_activity_ll_day_view);
		
		llWeekView = (LinearLayout) this.findViewById(R.id.past_records_activity_ll_week_view);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		
		tvYearTitle.setText(String.valueOf(year));
		tvMonthTitle.setText(String.valueOf(month+1));
		
		tabHead = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_day_tab_head);
		tabHead.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//选中了当前的时间
				if(dayViewFlag){
					showDayHistory(curSelectDate, MiraConstants.PART_HEAD);
				}else{
					showWeekHistory(curSelectDate, MiraConstants.PART_HEAD);
				}
			}
		});
		
		tabFace = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_day_tab_face);
		tabFace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(dayViewFlag){
					showDayHistory(curSelectDate, MiraConstants.PART_FACE);
				}else{
					showWeekHistory(curSelectDate, MiraConstants.PART_FACE);
				}
			}
		});
		
		tabNose = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_day_tab_nose);
		tabNose.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(dayViewFlag){
					showDayHistory(curSelectDate, MiraConstants.PART_NOSE);
					
				}else{
					showWeekHistory(curSelectDate, MiraConstants.PART_NOSE);
					
				}
			}
		});
		
		tabChin = (LinearLayout) this.findViewById(R.id.past_record_activity_ll_day_tab_chin);
		tabChin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(dayViewFlag){
					showDayHistory(curSelectDate, MiraConstants.PART_CHIN);
					
				}else{
					showWeekHistory(curSelectDate, MiraConstants.PART_CHIN);
					
				}
			}
		});
		
		//日历控件
		prDp = (DatePicker) this.findViewById(R.id.pastRecord_activity_DatePicker);
		prDp.isMultiSelect(false);
		prDp.setOnDateSelected(new OnDateSelected(){
			@Override
			public void selected(List<String> date) {
				Log.d(tag, "OnDateSelected selected()");
				curSelectDate = date.get(0);
				if (dayViewFlag) {
					showDayHistory(date.get(0), MiraConstants.PART_HEAD);

				} else {
					showWeekHistory(date.get(0), MiraConstants.PART_HEAD);

				}
			}});
		
		prDp.setOnMonthChange(new OnMonthChange(){
			@Override
			public void change(int month) {
				Log.d(tag, "OnMonthChange change");
				tvMonthTitle.setText(String.valueOf(month));
			}
		});
		
		prDp.setOnYearChange(new OnYearChange(){
			@Override
			public void change(int year) {
				Log.d(tag, "OnYearChange year");
				tvYearTitle.setText(String.valueOf(year));
			}
		});
		
		
		backbtn = (LinearLayout) this.findViewById(R.id.pastRecordBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRPastRecordsActivity.this.finish();
			}
		});
		
		btnViewSelect = (Button) this.findViewById(R.id.past_record_activity_btn_view_select);
		btnViewSelect.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String dayView = getString(R.string.pastRecord_activity_day_view);
				String weekView = getString(R.string.pastRecord_activity_week_view);
				
				String curText = (String) btnViewSelect.getText();
				//点击天视图
				if (dayView.equalsIgnoreCase(curText)) {
					btnViewSelect.setText(weekView);
					llDayView.setVisibility(View.VISIBLE);
					llWeekView.setVisibility(View.GONE);
					dayViewFlag = true;

				} else {
					btnViewSelect.setText(dayView);
					llDayView.setVisibility(View.GONE);
					llWeekView.setVisibility(View.VISIBLE);
					dayViewFlag = false;

				}
			}
		});
	}
	
	/**
	 * 重置天视图的展示数据
	 */
	private void resetDatView(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.width = 1;
		rlProgress1.setLayoutParams(params);
		tvProgressTime1.setText("");
		tvProgressScore1.setText(String.valueOf(""));
		rlProgress2.setLayoutParams(params);
		tvProgressTime2.setText("");
		tvProgressScore2.setText(String.valueOf(""));
		rlProgress3.setLayoutParams(params);
		tvProgressTime3.setText("");
		tvProgressScore3.setText(String.valueOf(""));
		rlProgress4.setLayoutParams(params);
		tvProgressTime4.setText("");
		tvProgressScore4.setText(String.valueOf(""));
		rlProgress5.setLayoutParams(params);
		tvProgressTime5.setText("");
		tvProgressScore5.setText(String.valueOf(""));
		tvAvgScore.setText("");
	}
	
	/**
	 * 切换显示的部位
	 * @param partType
	 */
	private void switchTab(int partType){
		Drawable btnOn = getResources().getDrawable(R.drawable.btn_on);
		Drawable btnOff = getResources().getDrawable(R.drawable.btn_off);
		switch (partType) {
		case MiraConstants.PART_HEAD:
			tabHead.setBackground(btnOn);
			tabFace.setBackground(btnOff);
			tabNose.setBackground(btnOff);
			tabChin.setBackground(btnOff);
			
			break;
		case MiraConstants.PART_FACE:
			tabHead.setBackground(btnOff);
			tabFace.setBackground(btnOn);
			tabNose.setBackground(btnOff);
			tabChin.setBackground(btnOff);
			
			break;
		case MiraConstants.PART_NOSE:
			tabHead.setBackground(btnOff);
			tabFace.setBackground(btnOff);
			tabNose.setBackground(btnOn);
			tabChin.setBackground(btnOff);
			
			break;
		case MiraConstants.PART_CHIN:
			tabHead.setBackground(btnOff);
			tabFace.setBackground(btnOff);
			tabNose.setBackground(btnOff);
			tabChin.setBackground(btnOn);
			
			break;

		default:
			break;
		}
	}
	
	/**
	 * 天视图显示数据
	 * @param date
	 */
	private void showDayHistory(String date, int partType){
		switchTab(partType);
		resetDatView();
		//选中了当前的时间
		if(date != null){
			try {
				Date curDate = sdf.parse(curSelectDate);
				long startTime = DateUtil.getTimesMorning(curDate);
				long endTime = DateUtil.getTimesNight(curDate);
				//今天的数据
				List<TestModel> list = MRTestBLL.getTestListByTime(partType, MRPastRecordsActivity.this, startTime, endTime);
				int avgScore = MRTestBLL.getTestAverage(partType, startTime, endTime, MRPastRecordsActivity.this);
				if(!list.isEmpty()){
					int width = llProgress1.getWidth();
					if(list.size() > 0){
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
						TestModel model = list.get(0);
						int score = model.shuiFen;
						long time = model.time;
						String timeStr = DateUtil.getHourMinute(time);
						params.width = (width * score)/100;
						rlProgress1.setLayoutParams(params);
						tvProgressTime1.setText(timeStr);
						tvProgressScore1.setText(String.valueOf(score));
						
					}
					
					if(list.size() > 1){
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
						TestModel model = list.get(1);
						int score = model.shuiFen;
						long time = model.time;
						String timeStr = DateUtil.getHourMinute(time);
						params.width = (width * score)/100;
						rlProgress2.setLayoutParams(params);
						tvProgressTime2.setText(timeStr);
						tvProgressScore2.setText(String.valueOf(score));
					}
					
					if(list.size() > 2){
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
						TestModel model = list.get(2);
						int score = model.shuiFen;
						long time = model.time;
						String timeStr = DateUtil.getHourMinute(time);
						params.width = (width * score)/100;
						rlProgress3.setLayoutParams(params);
						tvProgressTime3.setText(timeStr);
						tvProgressScore3.setText(String.valueOf(score));
						
					}
					
					if(list.size() > 3){
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
						TestModel model = list.get(3);
						int score = model.shuiFen;
						long time = model.time;
						String timeStr = DateUtil.getHourMinute(time);
						params.width = (width * score)/100;
						rlProgress4.setLayoutParams(params);
						tvProgressTime4.setText(timeStr);
						tvProgressScore4.setText(String.valueOf(score));
					}
					
					if(list.size() > 4){
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
						TestModel model = list.get(4);
						int score = model.shuiFen;
						long time = model.time;
						String timeStr = DateUtil.getHourMinute(time);
						params.width = (width * score)/100;
						rlProgress5.setLayoutParams(params);
						tvProgressTime5.setText(timeStr);
						tvProgressScore5.setText(String.valueOf(score));
						
					}
					tvAvgScore.setText(String.valueOf(avgScore));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 周视图显示数据
	 * @param date
	 */
	private void showWeekHistory(String date, int partType){
		switchTab(partType);
		//重置视图 //TODO
		
		Date curDate;
		try {
			curDate = sdf.parse(curSelectDate);
			int j = 6;
			List<String> x_coords = new ArrayList<String>();
			List<Integer> x_coord_values = new ArrayList<Integer>();
			
			for (int i = 0; i < 7; i++) {
				x_coords.add(DateUtil.getMonthDay(curDate, -j));
				long startTime = DateUtil.getTimesMorning(curDate, -j);
				long endTime = DateUtil.getTimesNight(curDate, -j);
				j--;
				int value = MRTestBLL.getTestMax(partType, startTime, endTime, MRPastRecordsActivity.this);
				x_coord_values.add(value);
			}
			lcv.setBgColor(Color.WHITE);
			lcv.setValue(x_coords, x_coord_values);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
