package com.common;

import java.io.File;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

public class MRDownload {
	static CompleteReceiver completeReceiver;
	static class CompleteReceiver extends BroadcastReceiver {
		//public static HashMap<long, String> downloadSavePath = new HashMap<long, String>();
		public String save_path = "";
		Context context;
		@Override
		public void onReceive(Context context, Intent intent) {
			// get complete download id
			//long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			System.out.println("download OK: "+save_path);
			this.context=context;
			downComplete(save_path);
			
			// to do here
		}
		
		private void downComplete(String filePath){
			System.out.println("filePath : "+filePath);
			File _file =  new File(filePath);
			Intent intent = new Intent();
			System.out.println("安装apk ："+_file.getName()+" : "+_file.length()+"--"+_file.getPath()+"--"+_file.canRead()+"--"+_file.exists());
			intent.setAction("android.intent.action.VIEW");//向用户显示数据
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//以新压入栈
			intent.addCategory("android.intent.category.DEFAULT");
			//intent.setType("application/vnd.android.package-archive");
			//intent.setData(Uri.fromFile(file));
			Uri abc = Uri.fromFile(_file);
			intent.setDataAndType(abc,
					"application/vnd.android.package-archive");
			
			context.startActivity(intent);
			completeReceiver=null;
		}

	};
	public static void downloadApk(String url,Context context){
		if(completeReceiver!=null)
		{
			Toast.makeText(context, "下载已启动...", Toast.LENGTH_SHORT).show();
			return;
		}
		completeReceiver=new CompleteReceiver();
		context.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));  

		DownloadManager downloadManager  = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);

		String apkUrl = url;
		String dir = isFolderExist("mira");
		completeReceiver.save_path = dir+"/mira.apk";
		
		File f = new File(dir+"/mira.apk");
		if(f.exists()) f.delete();
		
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
		
		request.setDestinationInExternalPublicDir("mira", "mira.apk");
		request.allowScanningByMediaScanner();//表示允许MediaScanner扫描到这个文件，默认不允许。
		request.setTitle("Mira程序更新");//设置下载中通知栏提示的标题
		request.setDescription("Mira程序更新正在下载中:"+dir);//设置下载中通知栏提示的介绍
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		
		@SuppressWarnings("unused")
		long downloadId = downloadManager.enqueue(request);
		
	}
	
	private static String isFolderExist(String dir) {
		File folder = Environment.getExternalStoragePublicDirectory(dir);
		boolean rs = (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
		return folder.getAbsolutePath();		 
	}
}
