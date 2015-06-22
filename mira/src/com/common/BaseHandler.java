package com.common;


import android.os.Message;

public interface BaseHandler <T>{
	public void handleMessage(Message msg);
	public void handleMessage(HttpResult<T> result);
	public void handleMessage(T result);
}
