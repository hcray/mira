package com.mira;

import java.util.Calendar;
import java.util.List;

import cn.mira.datepicker.interfaces.OnDateSelected;
import cn.mira.datepicker.interfaces.OnMonthChange;
import cn.mira.datepicker.interfaces.OnYearChange;
import cn.mira.datepicker.views.DatePicker;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MRPastRecordsActivity extends Activity {
	
	private TextView tvYearTitle;

	private TextView tvMonthTitle;
	
	private LinearLayout backbtn;
	
	private Button btnViewSelect;
	
	private LinearLayout llDayView;

	private LinearLayout llWeekView;
	
	private DatePicker prDp;
	
	private final static String tag = "MRPastRecordsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_past_records);
		
		tvYearTitle = (TextView) this.findViewById(R.id.pastRecord_activity_tv_year_title);

		tvMonthTitle = (TextView) this.findViewById(R.id.pastRecord_activity_tv_month_title);
		
		llDayView = (LinearLayout) this.findViewById(R.id.past_records_activity_ll_day_view);
		
		llWeekView = (LinearLayout) this.findViewById(R.id.past_records_activity_ll_week_view);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		
		tvYearTitle.setText(String.valueOf(year));
		tvMonthTitle.setText(String.valueOf(month+1));
		
		//日历控件
		prDp = (DatePicker) this.findViewById(R.id.pastRecord_activity_DatePicker);
		prDp.setOnDateSelected(new OnDateSelected(){
			@Override
			public void selected(List<String> date) {
				Log.d(tag, "OnDateSelected selected()");
				
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
				if(dayView.equalsIgnoreCase(curText)){
					btnViewSelect.setText(weekView);
					llDayView.setVisibility(View.GONE);
					llWeekView.setVisibility(View.VISIBLE);

				}else{
					btnViewSelect.setText(dayView);
					llDayView.setVisibility(View.VISIBLE);
					llWeekView.setVisibility(View.GONE);
				}
			}
		});
		
	}
}
