package com.common;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HandlerEvent<T> implements BaseHandler<T> {
	Looper mainLooper;
	boolean isMain;
	public T Class;

	@Override
	public void handleMessage(Message msg) {
	}

	@Override
	public void handleMessage(HttpResult<T> result) {
	}

	@Override
	public void handleMessage(T result) {
	}

	public void sendEmptyMessage(int what) {
		Message msg = new Message();
		msg.what = what;
		sendMessage(msg);
	}

	boolean isMain() {
		mainLooper = Looper.getMainLooper();
		if (Looper.myLooper() == mainLooper) {
			return true;
		} else {

			return false;
		}
	}

	public void sendMessage(final Message msg) {
		if (isMain()) {
			this.handleMessage(msg);
		} else {
			new Handler(mainLooper).post(new Runnable() {
				@Override
				public void run() {
					HandlerEvent.this.handleMessage(msg);
				}
			});
		}
	}

	public void sendData(final HttpResult<T> o) {
		if (isMain()) {
			this.handleMessage(o);
		} else {
			new Handler(mainLooper).post(new Runnable() {
				@Override
				public void run() {
					HandlerEvent.this.handleMessage(o);
				}
			});
		}
	}

	public void sendObject(final T o) {
		if (isMain()) {
			this.handleMessage(o);
		} else {
			new Handler(mainLooper).post(new Runnable() {
				@Override
				public void run() {
					HandlerEvent.this.handleMessage(o);
				}
			});
		}
	}

	public void post(Runnable r) {
		if (isMain()) {
			r.run();
		} else {
			new Handler(mainLooper).post(r);
		}
	}

	public void postDelayed(Runnable r, int time) {
		if (isMain()) {
			r.run();
		} else {
			new Handler(mainLooper).postDelayed(r, time);
		}
	}
}