package com.mira;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bll.MRTestBLL;
import com.common.MiraConstants;
import com.model.CityModel;
import com.umeng.analytics.MobclickAgent;
import com.view.MyLetterListView;
import com.view.MyLetterListView.OnTouchingLetterChangedListener;

public class MRCityActivity extends Activity {
	// 返回
	private LinearLayout backbtn;
	
	/**
	 * 定位到的城市
	 */
	private TextView cityLocation;

	private BaseAdapter adapter;
	private ListView mCityLit;
	private TextView overlay;
	private MyLetterListView letterListView;
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread;
//	private SQLiteDatabase database;
	private ArrayList<CityModel> mCityNames;
	private Button btn;
	private EditText et;
	
	private String city = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_city);
		btn = (Button) findViewById(R.id.btn);
		et = (EditText) findViewById(R.id.city_activity_input);
		overlay = (TextView) findViewById(R.id.overlay);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = et.getText().toString().trim();
				mCityNames.clear();
				mCityNames = MRTestBLL.getSelectCityNames(content, MRCityActivity.this);
				setAdapter(mCityNames);

			}
		});

		mCityLit = (ListView) findViewById(R.id.city_list);
		letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);
//		DBManager dbManager = new DBManager(this);
//		dbManager.openDateBase();
//		dbManager.closeDatabase();
//		database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
		mCityNames = MRTestBLL.getCityNames(MRCityActivity.this);
		// database.close();
		letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
		alphaIndexer = new HashMap<String, Integer>();
		handler = new Handler();
		overlayThread = new OverlayThread();
		// initOverlay();
		setAdapter(mCityNames);
		mCityLit.setOnItemClickListener(new CityListOnItemClick());

		// 返回按钮
		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRCityActivity.this.finish();
			}
		});
		
		//监控输入框的变化
		et.addTextChangedListener(watcher);
		
		cityLocation = (TextView) this.findViewById(R.id.city_activity_tv_city_location);
		SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("Location", Context.MODE_PRIVATE);
		city = preferences.getString("City", "");
		cityLocation.setText(city);
		cityLocation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.putExtra(MiraConstants.SELECTED_CITY, city);
				setResult(RESULT_OK, mIntent);
				finish();
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


	/**
	 * 城市列表点击事件
	 * 
	 * @author sy
	 * 
	 */
	class CityListOnItemClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
				long arg3) {
			CityModel cityModel = (CityModel) mCityLit.getAdapter().getItem(pos);
			//保存当前选中的城市，结束当前页面
			//Toast.makeText(MRCityActivity.this, cityModel.getCityName(), Toast.LENGTH_SHORT).show();

			SharedPreferences preferences = arg1.getContext().getSharedPreferences("Location", Context.MODE_PRIVATE);
			Editor editor = preferences.edit();
			editor.putString(MiraConstants.SELECTED_CITY, cityModel.getCityName());
			editor.commit();
			
			Intent mIntent = new Intent();
			mIntent.putExtra(MiraConstants.SELECTED_CITY, cityModel.getCityName());
			setResult(RESULT_OK, mIntent);
			finish();
		}

	}

	/**
	 * 为ListView设置适配器
	 * 
	 * @param list
	 */
	private void setAdapter(List<CityModel> list) {
		if (list != null) {
			adapter = new ListAdapter(this, list);
			mCityLit.setAdapter(adapter);
		}

	}

	/**
	 * ListViewAdapter
	 * 
	 * @author sy
	 * 
	 */
	private class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<CityModel> list;

		public ListAdapter(Context context, List<CityModel> list) {

			this.inflater = LayoutInflater.from(context);
			this.list = list;
			alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];

			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				// getAlpha(list.get(i));
				String currentStr = list.get(i).getNameSort();
				// 上一个汉语拼音首字母，如果不存在为“ ”
				String previewStr = (i - 1) >= 0 ? list.get(i - 1)
						.getNameSort() : " ";
				if (!previewStr.equals(currentStr)) {
					String name = list.get(i).getNameSort();
					alphaIndexer.put(name, i);
					sections[i] = name;
				}
			}

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.list_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText(list.get(position).getCityName());
			String currentStr = list.get(position).getNameSort();
			String previewStr = (position - 1) >= 0 ? list.get(position - 1)
					.getNameSort() : " ";
			if (!previewStr.equals(currentStr)) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			TextView alpha;
			TextView name;
		}

	}

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		LayoutInflater inflater = LayoutInflater.from(this);
		overlay = (TextView) inflater.inflate(R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	
	private class LetterListViewListener implements OnTouchingLetterChangedListener {
		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				mCityLit.setSelection(position);
				overlay.setText(sections[position]);
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}
	
	/**
	 * 输入框监控
	 */
	private TextWatcher watcher = new TextWatcher(){

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String content = et.getText().toString().trim();
			mCityNames.clear();
			mCityNames = MRTestBLL.getSelectCityNames(content, MRCityActivity.this);
			setAdapter(mCityNames);
		}

		@Override
		public void afterTextChanged(Editable s) {
			
		}};
}
