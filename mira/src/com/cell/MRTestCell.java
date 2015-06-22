package com.cell;

import java.text.SimpleDateFormat;

import com.mira.R;
import com.model.TestModel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MRTestCell extends FrameLayout {

	TextView tvDate;
	TextView tvTime;
	TextView tvShuiFen;
	TextView tvHuanJing;
	TextView tvStatus;
	Context context;

	public MRTestCell(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.mr_cell_test, this, true);
		this.context = context;
		tvDate=(TextView)findViewById(R.id.tv_date);
		tvTime=(TextView)findViewById(R.id.tv_time);
		tvShuiFen=(TextView)findViewById(R.id.tv_shuifen);
		tvHuanJing=(TextView)findViewById(R.id.tv_huanjing);
		tvStatus=(TextView)findViewById(R.id.tv_status);
	}
	public void setData(TestModel model)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(model.time);
		formatter = new SimpleDateFormat("HH:mm:ss");
		String time=formatter.format(model.time);
		tvDate.setText(date);
		tvTime.setText(time);
		tvShuiFen.setText("水分:"+model.shuiFen+"%");
		tvHuanJing.setText("温度:"+model.wenDu/100+" 湿度:"+model.shiDu+" 紫外线:"+model.ziWaiXian/10+"."+model.ziWaiXian%10);
		if(model.status==0)
		{
			tvStatus.setText("(护肤前)");
		}
		else
		{
			tvStatus.setText("(护肤后)");
		}
	}

}
