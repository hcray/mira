package com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bean.VersionResultBean;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mira.AppContext;
import com.mira.R;
import com.model.User;


public class UpdateManager {
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	
	//版本有更新
	private static final int UPDATE_YES= 3;
	//版本没有更新
	private static final int UPDATE_NO= 4;
	//连接服务器异常
	private static final int UPDATE_EXCEPTION= 5;
	
	
	/* 保存解析的XML信息 */
	HashMap<String, String> mHashMap = new HashMap<String, String>();
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {
//		int param = isUpdate(); 
//		if (param == UPDATE_YES) { //有更新
//			// 显示提示对话框
//			showNoticeDialog();
//		} else if(param == UPDATE_NO && prompt){
//			//没有更新
//			Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
//		} else if(prompt){
//			//连接服务器异常
//			Toast.makeText(mContext, R.string.soft_update_exception, Toast.LENGTH_LONG).show();
//		}
		
		User curUser = AppContext.getInstance().getLoginUser();
		String UserId = curUser.getUserId();
		String UUID = AppContext.getInstance().getAppId();
		int verCode = Tools.getVerCode(AppContext.context());
		HttpKit.versionDetection(UUID, UserId, 1, verCode, handler);
	}
	
	/**
	 * 修改用户后的回调
	 */
	private final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d("UpdateManager", "handler: " + response.toString());
			Gson gson = new Gson();
			VersionResultBean retBean = gson.fromJson(response.toString(), VersionResultBean.class);
			//TODO
			//retBean.setResultCode(0);
			//成功
			if(retBean.getResultCode() == 0){
				//retBean.setId(2);
				//retBean.setUrl("http://cyy2hxh.tunnel.mobi/TaxiAppUpateServer/download/mira.apk");
				//retBean.setName("mira2.0");
				Log.d("UpdateManager", retBean.toString());
				mHashMap.put("version", retBean.getCode());
				mHashMap.put("name", retBean.getName());
				mHashMap.put("url", retBean.getUrl());
				int serverCode = retBean.getId();
				int curCode = Tools.getVerCode(mContext);
				if(serverCode > curCode){
					showNoticeDialog();
				}else{
					Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
				}
				
			} else {
				Toast.makeText(mContext, retBean.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				String responseString, Throwable throwable) {
			Toast.makeText(mContext, R.string.soft_update_exception, Toast.LENGTH_LONG).show();
		}
		
	};

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 */
	private int isUpdate() {
		// 获取当前软件版本
		int versionCode = Tools.getVerCode(mContext);
		int retStr = UPDATE_EXCEPTION; //默认异常
		mHashMap = Tools.getServerVerInfo();		
		
		if (null != mHashMap) {
			int serviceCode = Integer.valueOf(mHashMap.get("version"));
			// 版本判断
			if (serviceCode > versionCode) {
				retStr = UPDATE_YES; //有更新
			}else{
				retStr = UPDATE_NO; //没有更新
			}
		}
		return retStr;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		// 构造对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setMessage(R.string.soft_update_info);
		// 更新
		builder.setPositiveButton(R.string.soft_update_updatebtn,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 显示下载对话框
						showDownloadDialog();
					}
				});
		// 稍后更新
		builder.setNegativeButton(R.string.soft_update_later,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		Dialog noticeDialog = builder.create();
		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_updating);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.softupdate_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		builder.setView(v);
		// 取消更新
		builder.setNegativeButton(R.string.soft_update_cancel,
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 设置取消状态
						cancelUpdate = true;
					}
				});
		mDownloadDialog = builder.create();
		mDownloadDialog.show();
		// 下载文件
		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 * @author 21829
	 *
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 获得存储卡的路径
					//String sdpath = Environment.getExternalStorageDirectory() + "/";
					//mSavePath = sdpath + "mira/download";
					
					mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mira/download/";
					URL url = new URL(mHashMap.get("url"));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(mSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(mSavePath, mHashMap.get("name"));
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						mHandler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, mHashMap.get("name"));
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
