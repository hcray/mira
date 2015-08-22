package com.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.bean.UpdateInfo;
import com.google.gson.Gson;

public class Tools {

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	/**
	 * 强制帮用户打开GPS
	 * 
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检测当的网络（WLAN、3G/2G）状态
	 * 
	 * @param context
	 *            Context
	 * @return true 表示网络可用
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}

	/***
	 * 获取版本号
	 * 
	 * @param context
	 * @return 软件版本号
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo("com.mira", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e("Tools", e.getMessage());
		}
		return verCode;
	}

	/***
	 * 获取版本名称
	 * 
	 * @param context
	 * @return 软件版本名称
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo("com.mira", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("Tools", e.getMessage());
		}
		return verName;
	}

	/**
	 * 返回服务器端软件版本
	 * 
	 * @return 软件版本的信息
	 */
	public static HashMap<String, String> getServerVerInfo() {
		HashMap<String, String> map = new HashMap<String, String>();
		// 在模拟器上可以用10.0.2.2代替127.0.0.1和localhost 另外如果是在局域网环境可以用
		// 192.168.0.x或者192.168.1.x(根据具体配置)连接本机
		// String url =
		// "http://10.36.23.143:8080/TaxiAppUpateServer/download/version.json";
		String url = "http://cyy2hxh.tunnel.mobi/TaxiAppUpateServer/download/version.json";
		// String url =
		// "https://work.dahuatech.com/webs/download/PhoneApp/version.json";
		String retJson = null;

		try {
			retJson = getContent(url);
			Log.d("Tools", "retJson: " + retJson);

			Gson gson = new Gson();
			UpdateInfo info = gson.fromJson(retJson, UpdateInfo.class);

			map.put("version", info.getVersion());
			map.put("name", info.getName());
			map.put("url", info.getUrl());

		} catch (Exception e) {
			Log.e("Tools", " getcontent() error : " + e.getMessage());
			e.printStackTrace();
			map = null;
		}

		return map;
	}

	/**
	 * 获取网址内容
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getContent(String url) throws Exception {
		StringBuilder sb = new StringBuilder();

		HttpClient client = new DefaultHttpClient();
		// HttpClient client = getNewHttpClient();
		HttpParams httpParams = client.getParams();
		// 设置网络超时参数
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 5000);

		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), "UTF-8"), 8192);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
		}

		return sb.toString();
	}

	/**
	 * 根据水分值计算评分值
	 * 
	 * @param water
	 *            水分值
	 * @return 评分值
	 */
	public static int getScore(int water) {
		int score = 0;
		if (water > 0) {
			if (water < 30 && water > 0) {
				score = water * 2;

			} else if (water < 70 && water >= 30) {
				score = water + 30;

			} else if (water > 70) {
				score = 100;
			}
		}
		return score;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 把bitmap转成圆形
	 * */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int r = 0;
		// 取最短边做边长
		if (width < height) {
			r = width;
		} else {
			r = height;
		}
		// 构建一个bitmap
		Bitmap backgroundBm = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		// new一个Canvas，在backgroundBmp上画图
		Canvas canvas = new Canvas(backgroundBm);
		Paint p = new Paint();
		// 设置边缘光滑，去掉锯齿
		p.setAntiAlias(true);
		RectF rect = new RectF(0, 0, r, r);
		// 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
		// 且都等于r/2时，画出来的圆角矩形就是圆形
		canvas.drawRoundRect(rect, r / 2, r / 2, p);
		// 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
		p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		// canvas将bitmap画在backgroundBmp上
		canvas.drawBitmap(bitmap, null, rect, p);
		return backgroundBm;
	}
	
	/**
	 * 根据文件名取得拍照日期
	 * @param name
	 * @return 日期
	 */
	public static String getDateByName(String name){
		String retStr = "";
		if(name.startsWith("mira_")){
			String str = name.split("_")[1];
			retStr = str.substring(4, 6)+ "-" + str.substring(6, 8)+ " " + str.substring(8,10) +":"+ str.substring(10,12);
		}
		return retStr;
	}

}
