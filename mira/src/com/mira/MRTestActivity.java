package com.mira;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.bll.MRTestBLL;
import com.common.BaiduLocation;
import com.common.BitConverter;
import com.device.MRBluetoothEvent;
import com.device.MRBluetoothManage;
import com.model.TestModel;
import com.view.RoundProgressBar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MRTestActivity extends Activity {
	private Long mExitTime = 0l;

	ImageButton ibLianJia;
	ImageButton ibETou;
	ImageButton ibBiZi;
	ImageButton ibXiaBa;
	RoundProgressBar pb;
	TextView tvWenDu;
	TextView tvShiDu;
	TextView tvZiWaiXian;
	ImageButton ibQian;
	ImageButton ibHou;
	View llType;
	View llTap;
	long updateTime;
	View mask;
	TextView tvData;
	TextView tvMask;
	float progressWidth;
	float progressMinWidth;
	float progressNum;
	
	float progressWenDu;
	float progressShiDu;
	float progressZiWaiXian;
	
	float progressWenDuTemp;
	float progressShiDuTemp;
	float progressZiWaiXianTemp;
	
	boolean progressWenDuType;
	boolean progressShiDuType;
	boolean progressZiWaiXianType;
	
	Timer progressWenDuTimer;
	Timer progressShiDuTimer;
	Timer progressZiWaiXianTimer;
	ArrayList<TestModel> dataList;
	boolean isTest;
	boolean isSave;
	int width;
	int height;
	int type=0;
	TestModel testModel;
	View rightBtn;
	TestModel weatherModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_test);
		ibLianJia=(ImageButton)findViewById(R.id.ib_lianjia);
		ibETou=(ImageButton)findViewById(R.id.ib_etou);
		ibBiZi=(ImageButton)findViewById(R.id.ib_bizi);
		ibXiaBa=(ImageButton)findViewById(R.id.ib_xiaba);
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
			}
		};
		ibLianJia.setOnClickListener(click);
		ibETou.setOnClickListener(click);
		ibBiZi.setOnClickListener(click);
		ibXiaBa.setOnClickListener(click);
		type=0;
		ibLianJia.setBackgroundResource(R.drawable.btn_lianjia_selected);
		pb=(RoundProgressBar)findViewById(R.id.pb);
		tvWenDu=(TextView)findViewById(R.id.tv_wendu);
		tvShiDu=(TextView)findViewById(R.id.tv_shidu);
		tvZiWaiXian=(TextView)findViewById(R.id.tv_ziwaixian);
		ibQian=(ImageButton)findViewById(R.id.ib_qian);
		ibHou=(ImageButton)findViewById(R.id.ib_hou);
		mask=findViewById(R.id.fl_mask);
		mask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		tvMask=(TextView)findViewById(R.id.tv_mask);
		llType=findViewById(R.id.ll_type);
		llType.setVisibility(View.GONE);
		tvData=(TextView)findViewById(R.id.tv_data);
		llTap=findViewById(R.id.ll_tap);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)llTap.getLayoutParams();
		lp.height=width*140/4/160;
		llTap.setLayoutParams(lp);
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			
			@Override
			public void run() {
				progressWidth=tvWenDu.getWidth();
				Restart();
				new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
					
					@Override
					public void run() {
						progressMinWidth=tvWenDu.getWidth();
						progressNum=(progressWidth-progressMinWidth)/100;
					}
				}, 100);
			}
		}, 100);
		ibQian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tvData.setText("实时环境数据");
				llType.setVisibility(View.GONE);
				testModel.status=0;
				MRTestBLL.addTestModel(testModel, MRTestActivity.this);
				Toast.makeText(MRTestActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
		ibHou.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tvData.setText("实时环境数据");
				llType.setVisibility(View.GONE);
				testModel.status=1;
				MRTestBLL.addTestModel(testModel, MRTestActivity.this);
				Toast.makeText(MRTestActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		});
		rightBtn=findViewById(R.id.rightbtn);
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(MRTestActivity.this, MRHistoryActivity.class);
				MRTestActivity.this.startActivity(intent);
				
			}
		});

		startTest();
	}
	void Restart()
	{
		tvWenDu.setText("0");
		tvShiDu.setText("0");
		tvZiWaiXian.setText("0.0");
		FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvWenDu.getLayoutParams();
		lp.width=LayoutParams.WRAP_CONTENT;
		tvWenDu.setLayoutParams(lp);
		lp=(FrameLayout.LayoutParams)tvShiDu.getLayoutParams();
		lp.width=LayoutParams.WRAP_CONTENT;
		tvShiDu.setLayoutParams(lp);
		lp=(FrameLayout.LayoutParams)tvZiWaiXian.getLayoutParams();
		lp.width=LayoutParams.WRAP_CONTENT;
		tvZiWaiXian.setLayoutParams(lp);
		pb.setProgress(0);
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		weatherModel=BaiduLocation.getWeather(this);
	}
	void startTest()
	{
		MRBluetoothManage.Init(this, new MRBluetoothEvent() {

			@Override
			public void ResultStatus(int result) {
				switch (result) {
				case MRBluetoothManage.MRBLUETOOTHSTATUS_NOT_OPEN:
					Intent enableBtIntent = new Intent(
							BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, 100);
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_INIT_OK:
					mask.setVisibility(View.VISIBLE);
					MRBluetoothManage.scanAndConnect();
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_INIT_OK");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_START:
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_SCAN_START");
					mask.setVisibility(View.VISIBLE);
					tvMask.setText("搜索设备中...");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_END:
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_SCAN_END");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_START:
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_START");
					mask.setVisibility(View.VISIBLE);
					tvMask.setText("设备已找到，连接中...");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_OK:
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_OK");
					mask.setVisibility(View.GONE);
					Toast.makeText(MRTestActivity.this, "设备已连接",
							Toast.LENGTH_SHORT).show();
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_CLOSE:
					Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_CLOSE");
					mask.setVisibility(View.VISIBLE);
					MRBluetoothManage.stop();
					MRBluetoothManage.Init(MRTestActivity.this, this);
					break;
				default:
					break;
				}

			}

			@Override
			public void ResultDevice(ArrayList<BluetoothDevice> deviceList) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ResultData(byte[] data) {
				byte[] wenDuByte = new byte[2];
				byte[] shiDuByte = new byte[2];
				byte[] shuiFenByte = new byte[2];
				byte[] ziWaiXianByte = new byte[2];
				System.arraycopy(data, 4, wenDuByte, 0, 2);
				System.arraycopy(data, 6, shiDuByte, 0, 2);
				System.arraycopy(data, 8, shuiFenByte, 0, 2);
				System.arraycopy(data, 10, ziWaiXianByte, 0, 2);
				TestModel model = new TestModel();
				model.wenDu = BitConverter.toShort(wenDuByte);
				model.shiDu = BitConverter.toShort(shiDuByte);
				model.shuiFen = BitConverter.toShort(shuiFenByte);
				model.ziWaiXian = BitConverter.toShort(ziWaiXianByte);
				if (System.currentTimeMillis() - updateTime > 5000
						&& llType.getVisibility() == View.GONE) {
					updateTime = System.currentTimeMillis();
					updateWenDuUI((float) model.wenDu / 100.0F);
					updateShiDuUI(model.shiDu);
					updateZiWaiXianUI(model.ziWaiXian);
				}
				if (model.shuiFen > 5 && !isTest) {
					isTest = true;
					dataList = new ArrayList<TestModel>();
					mask.setVisibility(View.VISIBLE);
					tvMask.setText("检测中，请勿放开...");
				} else if (model.shuiFen < 5 && isTest && dataList.size() < 5) {
					mask.setVisibility(View.GONE);
					Toast.makeText(MRTestActivity.this, "测试未完成，请重试...",
							Toast.LENGTH_SHORT).show();
					isTest = false;
				} else if (model.shuiFen > 5) {
					dataList.add(model);
					if (dataList.size() == 5) {
						mask.setVisibility(View.GONE);
						updateUI();
					}
				} else if (model.shuiFen < 5) {
					isTest = false;
				}

			}
		});
	}
	void updateUI()
	{
		int wenDu=0;
		int shiDu=0;
		int shuiFen=0;
		int ziWaiXian=0;
		for(int i=0;i<dataList.size();i++)
		{
			wenDu+=dataList.get(i).wenDu;
			shiDu+=dataList.get(i).shiDu;
			shuiFen+=dataList.get(i).shuiFen;
			ziWaiXian+=dataList.get(i).ziWaiXian;
		}
		wenDu/=dataList.size();
		shiDu/=dataList.size();
		shuiFen/=dataList.size();
		ziWaiXian/=dataList.size();
		pb.setProgress(shuiFen);
		updateWenDuUI((float)wenDu/100.0F);
		updateShiDuUI(shiDu);
		updateZiWaiXianUI(ziWaiXian);
		llType.setVisibility(View.VISIBLE);
		tvData.setText("检测环境数据");
		testModel=new TestModel();
		testModel.type=type;
		testModel.wenDu=(short)wenDu;
		testModel.shiDu=(short)shiDu;
		testModel.shuiFen=(short)shuiFen;
		testModel.ziWaiXian=(short)ziWaiXian;
		weatherModel=BaiduLocation.getWeather(this);
		if(weatherModel==null)
		{
			testModel.weathercity="";
			testModel.weatherpm=0;
			testModel.weatherwendu="";
			testModel.weatherziwaixian="";
		}
		else
		{
			testModel.weathercity=weatherModel.weathercity;
			testModel.weatherpm=weatherModel.weatherpm;
			testModel.weatherwendu=weatherModel.weatherwendu;
			testModel.weatherziwaixian=weatherModel.weatherziwaixian;
		}
		testModel.time=System.currentTimeMillis();
	}
	void updateWenDuUI(float progress)
	{
		progressWenDuTemp=progress;
		if(progressWenDu<progress)
		{
			progressWenDuType=true;
		}
		else if(progressWenDu>progress)
		{
			progressWenDuType=false;
		}
		else
		{
			return;
		}
		if(progressWenDuTimer!=null)
		{
			progressWenDuTimer.cancel();
			progressWenDuTimer=null;
		}
		progressWenDuTimer=new Timer();
		progressWenDuTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(progressWenDuType)
				{
					progressWenDu+=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressWenDu;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvWenDu.setText(String.valueOf(tempNum));
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvWenDu.getLayoutParams();
							lp.width=(int)(progressMinWidth+(Math.abs(x))*progressNum);
							tvWenDu.setLayoutParams(lp);
						}
					});
					
					if(progressWenDu>=progressWenDuTemp)
					{
						if(progressWenDuTimer!=null)
						{
							progressWenDuTimer.cancel();
							progressWenDuTimer=null;
						}
					}
				}
				else
				{
					progressWenDu-=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressWenDu;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvWenDu.setText(String.valueOf(tempNum));
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvWenDu.getLayoutParams();
							lp.width=(int)(progressMinWidth+(Math.abs(x))*progressNum);
							tvWenDu.setLayoutParams(lp);
						}
					});
					if(progressWenDu<=progressWenDuTemp)
					{
						if(progressWenDuTimer!=null)
						{
							progressWenDuTimer.cancel();
							progressWenDuTimer=null;
						}
					}
				}
			}
		}, 0, 10);
		
	}
	void updateShiDuUI(float progress)
	{
		progressShiDuTemp=progress;
		if(progressShiDu<progress)
		{
			progressShiDuType=true;
		}
		else if(progressShiDu>progress)
		{
			progressShiDuType=false;
		}
		else
		{
			return;
		}
		if(progressShiDuTimer!=null)
		{
			progressShiDuTimer.cancel();
			progressShiDuTimer=null;
		}
		progressShiDuTimer=new Timer();
		progressShiDuTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(progressShiDuType)
				{
					progressShiDu+=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressShiDu;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvShiDu.setText(String.valueOf(tempNum));
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvShiDu.getLayoutParams();
							lp.width=(int)(progressMinWidth+x*progressNum);
							tvShiDu.setLayoutParams(lp);
						}
					});
					
					if(progressShiDu>=progressShiDuTemp)
					{
						if(progressShiDuTimer!=null)
						{
							progressShiDuTimer.cancel();
							progressShiDuTimer=null;
						}
					}
				}
				else
				{
					progressShiDu-=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressShiDu;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvShiDu.setText(String.valueOf(tempNum));
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvShiDu.getLayoutParams();
							lp.width=(int)(progressMinWidth+x*progressNum);
							tvShiDu.setLayoutParams(lp);
						}
					});
					if(progressShiDu<=progressShiDuTemp)
					{
						if(progressShiDuTimer!=null)
						{
							progressShiDuTimer.cancel();
							progressShiDuTimer=null;
						}
					}
				}
			}
		}, 0, 10);
	}
	void updateZiWaiXianUI(float progress)
	{
		progressZiWaiXianTemp=progress;
		if(progressZiWaiXian<progress)
		{
			progressZiWaiXianType=true;
		}
		else if(progressZiWaiXian>progress)
		{
			progressZiWaiXianType=false;
		}
		else
		{
			return;
		}
		if(progressZiWaiXianTimer!=null)
		{
			progressZiWaiXianTimer.cancel();
			progressZiWaiXianTimer=null;
		}
		progressZiWaiXianTimer=new Timer();
		progressZiWaiXianTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(progressZiWaiXianType)
				{
					progressZiWaiXian+=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressZiWaiXian;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvZiWaiXian.setText(tempNum/10+"."+tempNum%10);
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvZiWaiXian.getLayoutParams();
							lp.width=(int)(progressMinWidth*3+x*progressNum/2);
							tvZiWaiXian.setLayoutParams(lp);
						}
					});
					
					if(progressZiWaiXian>=progressZiWaiXianTemp)
					{
						if(progressZiWaiXianTimer!=null)
						{
							progressZiWaiXianTimer.cancel();
							progressZiWaiXianTimer=null;
						}
					}
				}
				else
				{
					progressZiWaiXian-=0.3;
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						float x=progressZiWaiXian;
						@Override
						public void run() {
							int tempNum=(int)(x);
							tvZiWaiXian.setText(tempNum/10+"."+tempNum%10);
							FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)tvZiWaiXian.getLayoutParams();
							lp.width=(int)(progressMinWidth*3+x*progressNum/2);
							tvZiWaiXian.setLayoutParams(lp);
						}
					});
					if(progressZiWaiXian<=progressZiWaiXianTemp)
					{
						if(progressZiWaiXianTimer!=null)
						{
							progressZiWaiXianTimer.cancel();
							progressZiWaiXianTimer=null;
						}
					}
				}
			}
		}, 0, 10);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MRBluetoothManage.stop();
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
