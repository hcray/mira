package com.device;

import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class MRBluetoothManage {
	private final static String tag = "MRBluetoothManage";
	
	static final String DeviceName = "MiraBangOne";
	static final String ServiceUUID = "0000fff0-0000-1000-8000-00805f9b34fb";
	static final String CharacteristicUUID = "0000fff2-0000-1000-8000-00805f9b34fb";

	public static final int MRBLUETOOTHSTATUS_SCAN_START = 10;
	public static final int MRBLUETOOTHSTATUS_SCAN_END = 11;
	public static final int MRBLUETOOTHSTATUS_CONNECT_START = 12;
	public static final int MRBLUETOOTHSTATUS_CONNECT_OK = 13;
	public static final int MRBLUETOOTHSTATUS_CONNECT_CLOSE = 14;
	public static final int MRBLUETOOTHSTATUS_NOT_OPEN = -1;
	public static final int MRBLUETOOTHSTATUS_INIT_OK = 0;

	static Context context;
	static BluetoothAdapter bluetoothAdapter;
	static MRBluetoothEvent bluetoothEvent;
	//static Timer scanTimer;
	static boolean isScanOK;
	static boolean isInit;
	static BluetoothGatt bluetoothGatt;
	
	private static String mBluetoothDeviceAddress = "";

	public static synchronized int Init(Context context, MRBluetoothEvent event) {
		Log.d(tag, "init()...");
		isScanOK = false;
		isInit = false;
		MRBluetoothManage.context = context;
		bluetoothEvent = event;
		final BluetoothManager bluetoothManager = (BluetoothManager) MRBluetoothManage.context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run() {
				}
			});
			bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_NOT_OPEN);
			return MRBLUETOOTHSTATUS_NOT_OPEN;
		}
		isInit = true;
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_INIT_OK);
		return MRBLUETOOTHSTATUS_INIT_OK;
	}

	public static synchronized void scanAndConnect() {
		Log.d(tag, "scanAndConnect()...");
		if (!isInit) {
			return;
		}
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
		bluetoothAdapter.startLeScan(new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] arg2) {
				//ParcelUuid[] uuids = arg0.getUuids();
				if (!isScanOK && device.getName() != null
						&& device.getName().equals(DeviceName)) {
					Log.d(tag, "onLeScan() isScanOK: " + isScanOK);
					isScanOK = true;
					stopScan();
					connect(device);
				}
			}
		});
	}

	public static synchronized void scanAndConnect(UUID uuid) {
		Log.d(tag, "scanAndConnect()...");
		if (!isInit) {
			return;
		}
		UUID uuids[] = new UUID[1];
		uuids[0] = uuid;
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
		bluetoothAdapter.startLeScan(uuids, new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] arg2) {
				if (!isScanOK) {
					isScanOK = true;
					stopScan();
					connect(device);
				}
			}
		});

	}

	public static synchronized void startScan() {
		Log.d(tag, "startScan()...");
		if (!isInit) {
			return;
		}
		bluetoothAdapter.stopLeScan(new LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
		/*
		final HashMap<String, BluetoothDevice> deviceHashMap = new HashMap<String, BluetoothDevice>();
		if (scanTimer != null) {
			scanTimer.cancel();
			scanTimer = null;
		}
		scanTimer = new Timer("BluetoothScanTimer");
		scanTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				synchronized (deviceHashMap) {
					final ArrayList<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
					Iterator<Entry<String, BluetoothDevice>> iter = deviceHashMap
							.entrySet().iterator();
					while (iter.hasNext()) {
						Entry<String, BluetoothDevice> entry = iter.next();
						deviceList.add(entry.getValue());
					}
					new Handler(Looper.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
						}
					});
					bluetoothEvent.ResultDevice(deviceList);
				}
			}
		}, 1000, 1000);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		*/
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
		bluetoothAdapter.startLeScan(new LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				String mac = arg0.getAddress();
				/*
				synchronized (deviceHashMap) {
					if (!deviceHashMap.containsKey(mac)) {
						deviceHashMap.put(mac, arg0);
					}
				}
				*/
			}
		});
	}

	public static synchronized void stop() {
		Log.d(tag, "stop()...");
		if (!isInit) {
			return;
		}
		isScanOK = false;
//		if (scanTimer != null) {
//			scanTimer.cancel();
//			scanTimer = null;
//		}
		if (bluetoothGatt != null) {
			bluetoothGatt.disconnect();
			bluetoothGatt.close();
			//bluetoothGatt = null;
		}
		bluetoothAdapter.stopLeScan(new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
		//isInit = false;
	}

	public static synchronized void stopScan() {
		Log.d(tag, "stopScan()...");
		if (!isInit) {
			return;
		}
//		if (scanTimer != null) {
//			scanTimer.cancel();
//			scanTimer = null;
//		}
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_END);
		bluetoothAdapter.stopLeScan(new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
	}

	static synchronized void connect(BluetoothDevice device) {
		Log.d(tag,"connect()");
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
			}
		});
		
		bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_START);
		if (bluetoothGatt != null) {
			//bluetoothGatt.close();
			//bluetoothGatt = null;
		}
		
		//连接已经存在的情况
		if (mBluetoothDeviceAddress.equals(device.getAddress())
				&& bluetoothGatt != null) {
			Log.d(tag,"connect() device: " + device.getAddress());
			if (bluetoothGatt.connect()) {
				Log.d(tag,"connect() return");
				return;
			}
		}
		
		Log.d(tag, "重新创建gatt");
		bluetoothGatt = device.connectGatt(context, false,
				new BluetoothGattCallback() {
					@Override
					public void onCharacteristicRead(final BluetoothGatt gatt,
							final BluetoothGattCharacteristic characteristic,
							int status) {
						//Log.d(tag,"onCharacteristicRead()");
						if (status == 0) {
							final byte[] data = characteristic.getValue();
							new Handler(Looper.getMainLooper())
									.post(new Runnable() {
										@Override
										public void run() {
										}
									});
							bluetoothEvent.ResultData(data);
							new Handler(Looper.getMainLooper()).postDelayed(
									new Runnable() {

										@Override
										public void run() {
										}
									}, 100);
							gatt.readCharacteristic(characteristic);
						} else {

						}
						super.onCharacteristicRead(gatt, characteristic, status);
					}

					@Override
					public void onServicesDiscovered(BluetoothGatt gatt, int status) {
						Log.d(tag,"onServicesDiscovered()");
						BluetoothGattService service = gatt.getService(UUID.fromString(ServiceUUID));
						BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(CharacteristicUUID));
						new Handler(Looper.getMainLooper()).post(new Runnable() {
									@Override
									public void run() {
									}
								});
						bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_OK);
						gatt.readCharacteristic(characteristic);
						super.onServicesDiscovered(gatt, status);
					}

					@Override
					public void onConnectionStateChange(BluetoothGatt gatt,
							int status, int newState) {
						Log.d(tag,"onConnectionStateChange() status: " + status + " newState: " + newState);
						if (newState == BluetoothProfile.STATE_CONNECTED) {
//							 if (status != BluetoothGatt.GATT_SUCCESS) {
//					                Log.w("Fisken", "Disconnect and close");
//					                gatt.disconnect();
//					                gatt.close();
//					                bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_CLOSE);
//					           }else{
//					           }
							 stopScan();
							 gatt.discoverServices();
						//连接已经断开
						} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
							 if (status != BluetoothGatt.GATT_SUCCESS) {
					                Log.w("Fisken", "Disconnect and close");
					                gatt.disconnect();
					          }
							//gatt.disconnect();
							gatt.close();
//							new Handler(Looper.getMainLooper())
//									.post(new Runnable() {
//										@Override
//										public void run() {
//										}
//									});
							bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_CLOSE);
						}
						super.onConnectionStateChange(gatt, status, newState);
					}
				});
		bluetoothGatt.connect();
		mBluetoothDeviceAddress = device.getAddress();

	}
}
