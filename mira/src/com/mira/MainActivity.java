package com.mira;




import java.util.ArrayList;

import com.common.BaiDuLocationModel;
import com.common.BaiduLocation;
import com.common.HandlerEvent;
import com.common.MRCommon;
import com.device.MRBluetoothEvent;
import com.device.MRBluetoothManage;
import com.view.RoundProgressBar;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private RoundProgressBar mRoundProgressBar1, mRoundProgressBar2 ,mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
	private float progress = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cricle_progress);
		
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		mRoundProgressBar2 = (RoundProgressBar) findViewById(R.id.roundProgressBar2);
		mRoundProgressBar3 = (RoundProgressBar) findViewById(R.id.roundProgressBar3);
		mRoundProgressBar4 = (RoundProgressBar) findViewById(R.id.roundProgressBar4);
		mRoundProgressBar5 = (RoundProgressBar) findViewById(R.id.roundProgressBar5);
		
		((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MRBluetoothManage.Init(MainActivity.this, new MRBluetoothEvent() {
					
					@Override
					public void ResultStatus(int result) {
						switch (result) {
						case MRBluetoothManage.MRBLUETOOTHSTATUS_INIT_OK:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_INIT_OK");
							break;
						case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_START:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_SCAN_START");
							break;
						case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_END:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_SCAN_END");
							break;
						case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_START:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_START");
							break;
						case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_OK:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_OK");
							break;
						case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_CLOSE:
							Log.e("BluetoothManage:", "MRBLUETOOTHSTATUS_CONNECT_CLOSE");
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
						// TODO Auto-generated method stub
						Log.e("Data:", MRCommon.bytesToHexString(data));
					}
				});
				MRBluetoothManage.scanAndConnect();
				progress=0;
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(progress <= 100){
							progress += 0.3;
							int color=0xff000000;
							int red=0;
							int greed=0;
							if(progress<50)
							{
								red=0xf0;
								greed=(int)(0xf0*(progress-50)/50);
							}
							else
							{
								red=(int)(0xf0-0xf0*(progress-50)/50);
								greed=0xf0;
							}
							
							color=color+red*0x10000+greed*0x100;
							
//							System.out.println(progress);
							mRoundProgressBar1.roundProgressColor=color;
							mRoundProgressBar2.roundProgressColor=color;
							mRoundProgressBar3.roundProgressColor=color;
							mRoundProgressBar4.roundProgressColor=color;
							mRoundProgressBar5.roundProgressColor=color;
							
							mRoundProgressBar1.setProgress((float)progress);
							mRoundProgressBar2.setProgress((float)progress);
							mRoundProgressBar3.setProgress((float)progress);
							mRoundProgressBar4.setProgress((float)progress);
							mRoundProgressBar5.setProgress((float)progress);
							
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						MRBluetoothManage.stop();
					}
				}).start();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
