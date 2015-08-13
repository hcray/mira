package com.service;

import java.util.ArrayList;

import android.app.IntentService;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;

import com.common.BitConverter;
import com.device.MRBluetoothEvent;
import com.device.MRBluetoothManage;
import com.model.TestModel;

public class BluetoothService extends IntentService {
	private static final String SERVICE_NAME = "BluetoothService";
	
	private static final String tag = "BluetoothService";

	public BluetoothService() {
		this(SERVICE_NAME);
	}

	public BluetoothService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		MRBluetoothManage.Init(this, new MRBluetoothEvent() {

			@Override
			public void ResultStatus(int result) {
				switch (result) {
				case MRBluetoothManage.MRBLUETOOTHSTATUS_NOT_OPEN:
					//Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					//startActivityForResult(enableBtIntent, 100);
					Log.d(tag, "MRBLUETOOTHSTATUS_NOT_OPEN");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_INIT_OK:
					MRBluetoothManage.scanAndConnect();
					Log.d(tag, "MRBLUETOOTHSTATUS_INIT_OK");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_START:
					Log.d(tag, "MRBLUETOOTHSTATUS_SCAN_START");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_END:
					Log.d(tag, "MRBLUETOOTHSTATUS_SCAN_END");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_START:
					Log.d(tag, "MRBLUETOOTHSTATUS_CONNECT_START");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_OK:
					Log.d(tag, "MRBLUETOOTHSTATUS_CONNECT_OK");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_CLOSE:
					Log.d(tag, "MRBLUETOOTHSTATUS_CONNECT_CLOSE");
					MRBluetoothManage.stop();
					MRBluetoothManage.Init(BluetoothService.this, this);
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
				Log.d(tag, model.toString());
				sendData(model);
			}
		});
	}
	
	/**
	 * 广播检测出来的数据
	 * @param model
	 */
	public void sendData(TestModel model){
		Intent intent = new Intent();//创建Intent对象
        intent.setAction("com.mira.action.BLUETOOTH_DATA");
        intent.putExtra("wenDu", model.wenDu);
        intent.putExtra("shiDu", model.shiDu);
        intent.putExtra("shuiFen", model.shuiFen);
        intent.putExtra("ziWaiXian", model.ziWaiXian);
        sendBroadcast(intent);//发送广播	
	}

}
