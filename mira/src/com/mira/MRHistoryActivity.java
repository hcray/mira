package com.mira;

import java.util.ArrayList;

import com.bll.MRTestBLL;
import com.cell.MRTestCell;
import com.model.TestModel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MRHistoryActivity extends Activity {

	class HistoryAdapter extends BaseAdapter
	{
		ArrayList<TestModel> list=new ArrayList<TestModel>();
		Context context;
		public HistoryAdapter(ArrayList<TestModel> list,Context context)
		{
			this.context=context;
			this.list=list;
		}
		public void setData(ArrayList<TestModel> list)
		{
			this.list =list;
			this.notifyDataSetChanged();
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return this.list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MRTestCell cell=null;
			if(convertView==null)
			{
				cell=new MRTestCell(context, null);
			}
			else
			{
				cell=(MRTestCell)convertView;
			}
			TestModel model=list.get(position);
			cell.setData(model);
			return cell;
		}
		
	}
	ImageButton ibLianJia;
	ImageButton ibETou;
	ImageButton ibBiZi;
	ImageButton ibXiaBa;
	View llTap;
	View leftBtn;
	int type;
	ListView listView;
	HistoryAdapter adapter;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	TextView tv6;
	TextView tv7;
	TextView tv8;
	LinearLayout llMask;
	ArrayList<TestModel> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_history);
		ibLianJia=(ImageButton)findViewById(R.id.ib_lianjia);
		ibETou=(ImageButton)findViewById(R.id.ib_etou);
		ibBiZi=(ImageButton)findViewById(R.id.ib_bizi);
		ibXiaBa=(ImageButton)findViewById(R.id.ib_xiaba);
		llTap=findViewById(R.id.ll_tap);
		tv1=(TextView)findViewById(R.id.tv1);
		tv2=(TextView)findViewById(R.id.tv2);
		tv3=(TextView)findViewById(R.id.tv3);
		tv4=(TextView)findViewById(R.id.tv4);
		tv5=(TextView)findViewById(R.id.tv5);
		tv6=(TextView)findViewById(R.id.tv6);
		tv7=(TextView)findViewById(R.id.tv7);
		tv8=(TextView)findViewById(R.id.tv8);
		llMask=(LinearLayout)findViewById(R.id.ll_mask);
		llMask.setVisibility(View.GONE);
		llMask.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				llMask.setVisibility(View.GONE);
			}
		});
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)llTap.getLayoutParams();
		lp.height=width*140/4/160;
		llTap.setLayoutParams(lp);
		listView=(ListView)findViewById(R.id.listView);
		OnClickListener click=new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ibLianJia.setBackgroundResource(R.drawable.btn_lianjia);
				ibETou.setBackgroundResource(R.drawable.btn_etou);
				ibBiZi.setBackgroundResource(R.drawable.btn_bizi);
				ibXiaBa.setBackgroundResource(R.drawable.btn_xiaba);
				switch(v.getId())
				{
					case R.id.ib_lianjia:
						type=0;
						ibLianJia.setBackgroundResource(R.drawable.btn_lianjia_selected);
						break;
					case R.id.ib_etou:
						type=1;
						ibETou.setBackgroundResource(R.drawable.btn_etou_selected);
						break;
					case R.id.ib_bizi:
						type=2;
						ibBiZi.setBackgroundResource(R.drawable.btn_bizi_selected);
						break;
					case R.id.ib_xiaba:
						type=3;
						ibXiaBa.setBackgroundResource(R.drawable.btn_xiaba_selected);
						break;
				}
				getDB();
			}
		};
		ibLianJia.setOnClickListener(click);
		ibETou.setOnClickListener(click);
		ibBiZi.setOnClickListener(click);
		ibXiaBa.setOnClickListener(click);
		leftBtn=findViewById(R.id.leftbtn);
		leftBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MRHistoryActivity.this.finish();
			}
		});
		type=0;
		ibLianJia.setBackgroundResource(R.drawable.btn_lianjia_selected);
		adapter=new HistoryAdapter(new ArrayList<TestModel>(), this);
		listView.setAdapter(adapter);
		getDB();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TestModel model=list.get(0);
				llMask.setVisibility(View.VISIBLE);
				tv1.setText(model.weathercity);
				tv2.setText("皮肤水分："+model.shuiFen);
				tv3.setText("环境温度："+model.wenDu/100);
				tv4.setText("环境湿度："+model.shiDu);
				tv5.setText("环境紫外线："+model.ziWaiXian);
				tv6.setText("城市温度："+model.weatherwendu);
				tv7.setText("PM2.5："+model.weatherpm);
				tv8.setText("城市紫外线："+model.weatherziwaixian);
			}
		});
	}
	void getDB()
	{
		list=MRTestBLL.getTestList(type, this);
		adapter.setData(list);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrhistory, menu);
		return true;
	}

}
