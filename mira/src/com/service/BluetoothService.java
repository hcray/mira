package com.service;

import java.util.UUID;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ble.BluetoothLeClass;
import com.ble.BluetoothLeClass.OnDataAvailableListener;
import com.ble.BluetoothLeClass.OnServiceDiscoverListener;
import com.common.BitConverter;
import com.model.TestModel;
import com.utils.Utils;

public class BluetoothService extends IntentService{
	private static final String SERVICE_NAME = "BluetoothService";
	
	private static final String TAG = "BluetoothService";
	
	public static final String DeviceName = "MiraBangOne";
	public static final String ServiceUUID = "0000fff0-0000-1000-8000-00805f9b34fb";
	public static final String CharacteristicUUID = "0000fff2-0000-1000-8000-00805f9b34fb";
	
	// 搜索BLE终端
	private BluetoothAdapter mBluetoothAdapter;
	// 读写BLE终端
	private BluetoothLeClass mBLE;


	public BluetoothService() {
		this(SERVICE_NAME);
	}

	public BluetoothService(String name) {
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		// Initializes a Bluetooth adapter. For API level 18 and above, get a
		// reference to
		// BluetoothAdapter through BluetoothManager.
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();

		// Checks if Bluetooth is supported on the device.
		if (mBluetoothAdapter == null) {
			Log.i(TAG, "Bluetooth is not supported");
			return;
		} else {
			Log.i(TAG, "mBluetoothAdapter = " + mBluetoothAdapter);
		}

		//启用蓝牙
		//mBluetoothAdapter.enable();
		//Log.i(TAG, "mBluetoothAdapter.enable");
		
		mBLE = new BluetoothLeClass(this);
		
		if (!mBLE.initialize()) {
			Log.e(TAG, "Unable to initialize Bluetooth");
		}
		Log.i(TAG, "mBLE = e" + mBLE);

		// 发现BLE终端的Service时回调
		mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);

		// 收到BLE终端数据交互的事件
		mBLE.setOnDataAvailableListener(mOnDataAvailable);
		
		//开始扫描
		mBLE.scanLeDevice(true);
		
		/*
		MRBluetoothManage.Init(this, new MRBluetoothEvent() {
			@Override
			public void ResultStatus(int result) {
				switch (result) {
				case MRBluetoothManage.MRBLUETOOTHSTATUS_NOT_OPEN:
					//Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					//startActivityForResult(enableBtIntent, 100);
					Log.d(TAG, "MRBLUETOOTHSTATUS_NOT_OPEN");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_INIT_OK:
					MRBluetoothManage.scanAndConnect();
					Log.d(TAG, "MRBLUETOOTHSTATUS_INIT_OK");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_START:
					Log.d(TAG, "MRBLUETOOTHSTATUS_SCAN_START");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_SCAN_END:
					Log.d(TAG, "MRBLUETOOTHSTATUS_SCAN_END");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_START:
					Log.d(TAG, "MRBLUETOOTHSTATUS_CONNECT_START");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_OK:
					Log.d(TAG, "MRBLUETOOTHSTATUS_CONNECT_OK");
					break;
				case MRBluetoothManage.MRBLUETOOTHSTATUS_CONNECT_CLOSE:
					Log.d(TAG, "MRBLUETOOTHSTATUS_CONNECT_CLOSE");
					MRBluetoothManage.stop();
					MRBluetoothManage.scanAndConnect();
					//MRBluetoothManage.Init(BluetoothService.this, this);
					break;
				default:
					break;
				}
			}

			@Override
			public void ResultDevice(ArrayList<BluetoothDevice> deviceList) {
				
			}

			@Override
			public void ResultData(byte[] data) {
				Log.i(TAG, data.toString());
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
				//Log.d(TAG, model.toString());
				sendData(model);
			}
		});
		*/
	}
	
	/**
	 * 搜索到BLE终端服务的事件
	 */
	private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new OnServiceDiscoverListener() {

		@Override
		public void onServiceDiscover(BluetoothGatt gatt) {
			//displayGattServices(mBLE.getSupportedGattServices());
			BluetoothGattService service = gatt.getService(UUID.fromString(ServiceUUID));
			BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(CharacteristicUUID));
			gatt.readCharacteristic(characteristic);
		}
	};

	/**
	 * 收到BLE终端数据交互的事件
	 */
	private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new OnDataAvailableListener() {
		/**
		 * BLE终端数据被读的事件
		 */
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			Log.i(TAG, "mOnDataAvailable.onCharacteristicRead() status: " + status);
			//读取到数据
			if (status == BluetoothGatt.GATT_SUCCESS) {
				final byte[] data = characteristic.getValue();
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
				Log.d(TAG, model.toString());
				sendData(model);
			}
			// 执行 mBLE.readCharacteristic(gattCharacteristic); 后就会收到数�? if
			// (status == BluetoothGatt.GATT_SUCCESS)
			Log.e(TAG,
					"onCharRead " + gatt.getDevice().getName() + " read "
							+ characteristic.getUuid().toString() + " -> "
							+ Utils.bytesToHexString(characteristic.getValue()));
			gatt.readCharacteristic(characteristic);
//
//			StartActivity.onCharacteristicRead(gatt, characteristic);
		}

		/**
		 * 收到BLE终端写入数据回调
		 */
		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			Log.e(TAG, "onCharWrite " + gatt.getDevice().getName() + " write "
					+ characteristic.getUuid().toString() + " -> "
					+ new String(characteristic.getValue()));

			//StartActivity.onCharacteristicRead(gatt, characteristic);
		}
	};
	
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
	
	@Override
	public void onDestroy() {
		super.onDestroy();
//		scanLeDevice(false);
//		mBLE.disconnect();
//		mBLE.close();
	}

}
