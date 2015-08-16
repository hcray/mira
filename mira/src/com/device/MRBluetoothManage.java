package com.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
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
	static Timer scanTimer;
	static boolean isScanOK;
	static boolean isInit;
	static BluetoothGatt bluetoothGatt;

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
					bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_NOT_OPEN);
				}
			});
			return MRBLUETOOTHSTATUS_NOT_OPEN;
		}
		isInit = true;
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_INIT_OK);
			}
		});
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
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
			}
		});
		bluetoothAdapter.startLeScan(new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				ParcelUuid[] uuids = arg0.getUuids();
				if (!isScanOK && arg0.getName() != null
						&& arg0.getName().equals(DeviceName)) {
					isScanOK = true;
					stopScan();
					connect(arg0);
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
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
			}
		});
		bluetoothAdapter.startLeScan(uuids, new LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				if (!isScanOK) {
					isScanOK = true;
					stopScan();
					connect(arg0);
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
							bluetoothEvent.ResultDevice(deviceList);
						}
					});
				}
			}
		}, 1000, 1000);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_START);
			}
		});
		bluetoothAdapter.startLeScan(new LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				String mac = arg0.getAddress();
				synchronized (deviceHashMap) {
					if (!deviceHashMap.containsKey(mac)) {
						deviceHashMap.put(mac, arg0);
					}
				}
			}
		});
	}

	public static synchronized void stop() {
		Log.d(tag, "stop()...");
		if (!isInit) {
			return;
		}
		isScanOK = true;
		if (scanTimer != null) {
			scanTimer.cancel();
			scanTimer = null;
		}
		if (bluetoothGatt != null) {
			bluetoothGatt.disconnect();
			bluetoothGatt.close();
			bluetoothGatt = null;
		}
		bluetoothAdapter.stopLeScan(new LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
		isInit = false;
	}

	public static synchronized void stopScan() {
		Log.d(tag, "stopScan()...");
		if (!isInit) {
			return;
		}
		if (scanTimer != null) {
			scanTimer.cancel();
			scanTimer = null;
		}
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_SCAN_END);
			}
		});
		bluetoothAdapter.stopLeScan(new LeScanCallback() {

			@Override
			public void onLeScan(BluetoothDevice arg0, int arg1, byte[] arg2) {
				// TODO Auto-generated method stub

			}
		});
	}

	static void connect(BluetoothDevice Device) {
		Log.d(tag,"connect()");
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_START);
			}
		});
		if (bluetoothGatt != null) {
			bluetoothGatt.close();
			bluetoothGatt = null;
		}
		bluetoothGatt = Device.connectGatt(context, false,
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
											bluetoothEvent.ResultData(data);
										}
									});
							new Handler(Looper.getMainLooper()).postDelayed(
									new Runnable() {

										@Override
										public void run() {
											gatt.readCharacteristic(characteristic);
										}
									}, 100);
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
										bluetoothEvent.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_OK);
									}
								});
						gatt.readCharacteristic(characteristic);
						super.onServicesDiscovered(gatt, status);
					}

					@Override
					public void onConnectionStateChange(BluetoothGatt gatt,
							int status, int newState) {
						Log.d(tag,"onConnectionStateChange()");
						if (newState == 2) {
							gatt.discoverServices();
						} else if (status == 0 && newState == 0) {
							gatt.close();
							gatt.disconnect();
							new Handler(Looper.getMainLooper())
									.post(new Runnable() {
										@Override
										public void run() {
											bluetoothEvent
													.ResultStatus(MRBLUETOOTHSTATUS_CONNECT_CLOSE);
										}
									});
						}
						super.onConnectionStateChange(gatt, status, newState);
					}
				});
		bluetoothGatt.connect();

	}
}
