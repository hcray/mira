package com.device;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;

public interface MRBluetoothEvent {
	public void ResultData(byte[] data);
	public void ResultStatus(int result);
	public void ResultDevice(ArrayList<BluetoothDevice> deviceList);
}
